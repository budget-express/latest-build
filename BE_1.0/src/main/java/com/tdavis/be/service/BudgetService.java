package com.tdavis.be.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;

import org.springframework.stereotype.Service;

import com.tdavis.be.entity.Budget;
import com.tdavis.be.entity.FileUpload;
import com.tdavis.be.entity.Project;
import com.tdavis.be.entity.Quote;
import com.tdavis.be.repository.BudgetRepository;
import com.tdavis.be.repository.ProjectRepository;

@Service
@Transactional
public class BudgetService {
	
	@Autowired
	private BudgetRepository budgetRepository;
	
	@Autowired
	private ProjectService projectService;
	
	@Autowired
	private QuoteService quoteService;
	
	@Autowired
	private HistoryService logger;
	
	/***************************************************************************************************************************
	 * 
	 * Recall Data from Repository 
	 * 
	 ***************************************************************************************************************************/
	
	/* Recall Data
	 * Budget findAll
	 */
	public Iterable<Budget> findAll () {
		
		return budgetRepository.findAll();
	}
	
	/* Recall Data
	 * Budget findById
	 */
	public Budget findById(int id){
		return budgetRepository.findById(id);
	}
	
	/* Recall Data
	 *  Find Number of quotes/budgets by Project
	 */
	public List<Integer> findNumbers(int id) {
		
		int files = 0;
		int quotes = 0;

		Budget budget = budgetRepository.findById(id);
		List<Integer> values = new ArrayList<>();
		
		for (Quote quote : budget.getQuotes()) {
			if (quote.getId() != null) {
				for (FileUpload file : quote.getFileUploads()){
					if (file.getId() != null) {
						files++;
					}
				}
				quotes++;
			}
		}			

		values.add(files);
		values.add(quotes);
		
		return values;
	}
	/* Recall Data
	 *  Find Number of budgets by Project
	 */
	public int getCount(Project project) {
		int count =  0;
		for(@SuppressWarnings("unused") Budget i: project.getBudgets()) {
		   count++;
		}

		return count;
	}
	/***************************************************************************************************************************
	 * 
	 * Interact with Repository 
	 * 
	 ***************************************************************************************************************************/
	
	/*
	 * Save Budget
	 */
	@PreAuthorize("hasRole('ROLE_USER')")
	public void save(Budget budget) {
		
		//Find Project
		Project project = projectService.findById(budget.getProject().getId());
		
		//Logging Message
		String message;

		//If...Existing Budget...Update Budget in Repository
		if (budget.getId() != null){
			
			//Preserve Created Date and Created By
			budget.setDateCreated(budgetRepository.getOne(budget.getId()).getDateCreated());
			budget.setCreatedBy(budgetRepository.getOne(budget.getId()).getCreatedBy());
			budget.setQuotes(budgetRepository.getOne(budget.getId()).getQuotes());

				
			//Set Edited Date and Edited By
			budget.setDateEdited(new Date());
			budget.setEditedBy(logger.getLoggedon());
			
			message = "Updated Budget: " + budget.getName();
		} else {
						
			//Set Created Date and Created By and Year
			budget.setDateCreated(new Date());
			budget.setCreatedBy(logger.getLoggedon());

			message = "Saved Budget: " + budget.getName()+" to Project: " + project.getName();
		}
		
		//Set Project and Year
		budget.setYear(project.getYear());
		budget.setProject(project);

		//Budget Calculations and Active
		Budget temp = update(budget);
		
		//Save Budget
		budgetRepository.save(temp);
		logger.info("project", project.getId(), message);
		budgetRepository.flush();
		
		//Update Project
		projectService.save(project);
		
		
		
	}

	/*
	 * Refresh Budget
	 */
	public void refresh() {
		
		//Refresh Quotes
		quoteService.refresh();
		
		for (Budget budget : findAll()) {
			save(budget);
		}
		logger.info("system", 0, "Refreshing Budgets!");
	}
	
	/*
	 * Update Budget
	 */
	public Budget update(Budget budget) {
		
		//Temp Budget
		Budget temp = budget;
				
		//Calculations for Quote
		temp = quoteCal(temp);
	
		//Calculations for Budget + Check Quarters
		temp = updateQuarters(temp);
		
		//Set Active Status
		temp = active(temp);
				
		return temp;
	}
	
	/*
	 * Delete Budget
	 */
	@PreAuthorize("hasRole('ROLE_USER')")
	public void delete(int id) {

		//Temp Budget
		Budget temp = budgetRepository.getOne(id);
		int projectId = temp.getProject().getId();
		
		//Delete Budget
		budgetRepository.delete(temp);
		logger.info("project", temp.getProject().getId(), "Deleted Budget "+ temp.getName()+" from Project: " + temp.getProject().getName());
		
		budgetRepository.flush();
		
		//Update Project
		projectService.save(projectService.findById(projectId));
	}
	
	/***************************************************************************************************************************
	 * 
	 * Utility Functions
	 * 
	 ***************************************************************************************************************************/
	
	/*
	 * Calculate Quote amounts for Budget
	 */
	public Budget quoteCal(Budget budget) {
		
		//Quote Calculation Variables
		double quoteSpentCapex=0;
		double quotePendingCapex=0;
		double quoteStagedCapex=0;
		double quoteSpentOpex=0;
		double quotePendingOpex=0;
		double quoteStagedOpex=0;
				
		//Calculate Quote Amounts
		if (budget.getQuotes() != null) {
			for (Quote quote : budget.getQuotes()){
				switch(quote.getStatus().toLowerCase()) {
					case "paid" :
						quoteSpentCapex += quote.getCapex();
						quoteSpentOpex += quote.getOpex();
						break;
					case "pending" :
						quotePendingCapex += quote.getCapex();
						quotePendingOpex += quote.getOpex();
						break;
					case "staged" :
						quoteStagedCapex += quote.getCapex();
						quoteStagedOpex =+ quote.getOpex();
						break;
					case "canceled" :
					default :
						break;
				}
			}
			
		}

		//Save Calculations to Budget
		budget.setQuoteSpent(quoteSpentCapex);
		budget.setQuotePending(quotePendingCapex);
		budget.setQuoteStaged(quoteStagedCapex);
		budget.setQuoteSpentOpex(quoteSpentOpex);
		budget.setQuotePendingOpex(quotePendingOpex);
		budget.setQuoteStagedOpex(quoteStagedOpex);
		
		return budget;
	}
	
	/*
	 * Calculate Budget amounts for Quarters and set timestamps
	 */
	private Budget updateQuarters(Budget budget) {
		
		//Budget Calculation Variables
		double budgetRemaining;
		double budgetRequested = 0;
		double budgetApproved = 0;		
		
		switch (budget.getStatus().toLowerCase()) {
			
			case "open" :
			case "closed" :
				//Set Quarters enabled/Disabled + Calculate Budget Amounts
				if (budget.isEnabledQ1()) {
					if (budget.getDateQ1Enabled() == null) {
						budget.setDateQ1Enabled(new Date());
					}
					budgetApproved += budget.getQ1();
				} else {
					budget.setDateQ1Enabled(budget.getDateQ1Enabled());
					budget.setDateQ1Disabled(new Date());
				}
				
				if (budget.isEnabledQ2()) {
					if (budget.getDateQ2Enabled() == null) {
						budget.setDateQ2Enabled(new Date());
					}
					budgetApproved += budget.getQ2();
				} else {
					budget.setDateQ2Enabled(budget.getDateQ2Enabled());
					budget.setDateQ2Disabled(new Date());
				}
				if (budget.isEnabledQ3()) {
					if (budget.getDateQ3Enabled() == null) {
						budget.setDateQ3Enabled(new Date());
					}
					budgetApproved += budget.getQ3();
				} else {
					budget.setDateQ3Enabled(budget.getDateQ3Enabled());
					budget.setDateQ3Disabled(new Date());
				}
				if (budget.isEnabledQ4()) {
					if (budget.getDateQ4Enabled() == null) {
						budget.setDateQ4Enabled(new Date());
					}
					budgetApproved += budget.getQ4();
				} else {
					budget.setDateQ4Enabled(budget.getDateQ4Enabled());
					budget.setDateQ4Disabled(new Date());
				}
				budgetRequested = budget.getQ1() + budget.getQ2() + budget.getQ3() + budget.getQ4();
				break;
			case "planning" :
			default :
				break;
		}
		
		budgetRemaining = budgetApproved - budget.getQuoteSpent();
		
		//Save Calculation to Budget
		budget.setBudgetRequested(budgetRequested);
		budget.setBudgetApproved(budgetApproved);
		budget.setBudgetRemaining(budgetRemaining);
		
		return budget;
	}
	
	public Budget active(Budget budget) {
		
		switch(budget.getStatus().toLowerCase()) {
		case "open" :
			budget.setDateEnabled(new Date());
			budget.setEnabledBy(logger.getLoggedon());
			break;
		case "closed" :
			budget.setDateEnabled(budgetRepository.getOne(budget.getId()).getDateEnabled());
			budget.setEnabledBy(budgetRepository.getOne(budget.getId()).getEnabledBy());
			budget.setDateDisabled(new Date());
			budget.setDisabledBy(logger.getLoggedon());
			break;
		case "planning" :
		default :
			break;
		}
		
		return budget;
	}
		
}

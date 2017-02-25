package com.tdavis.be.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;

import org.springframework.stereotype.Service;

import com.tdavis.be.entity.Budget;

import com.tdavis.be.entity.Project;
import com.tdavis.be.entity.Quote;
import com.tdavis.be.repository.BudgetRepository;



@Service
@Transactional
public class BudgetService {
	
	//Log output to console
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	//Setup Date Format
	private static final DateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
	
	@Autowired
	private BudgetRepository budgetRepository;
	
	@Autowired
	private ProjectService projectService;
	
	@Autowired
	private QuoteService quoteService;
	
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
	
	/***************************************************************************************************************************
	 * 
	 * Interact with Repository 
	 * 
	 ***************************************************************************************************************************/
	
	/*
	 * Save Budget
	 */
	public void save(Budget budget) {
		
		//Find Project
		Project project = projectService.findById(budget.getProject().getId());
		
		//Set Created Timestamp
		String time = sdf.format(new Date());
		if (budget.getDateCreated() == null){
			budget.setDateCreated(time);
		}
		
		//Calculations for Quote
		budget = quoteCal(budget);
	
		//Calculations for Budget
		budget = budgetCal(budget);
		
		//Set Parent Project
		budget.setProject(project);
		
		//Save Budget to Repository
		budgetRepository.save(budget);
		logger.info("*Service* Saved Budget: " + budget.getName()+" to Project: " + project.getName());
		
		//Update Project
		projectService.update(budget.getProject());
	}
	
	/*
	 * Refresh Budget
	 */
	public void refresh() {
		
		//Refresh Quotes
		quoteService.refresh();
		
		for (Budget budget : findAll()) {
			update(budget);
		}
		logger.info("*Service* Refreshing Budgets!");
	}
	
	/*
	 * Update Budget
	 */
	public void update(Budget budget) {
		
		//Temp Budget
		Budget temp = budget;
		
		//Set Edited Timestamp
		String time = sdf.format(new Date());
		temp.setDateEdited(time);	
		
		//Calculations for Quote
		temp = quoteCal(temp);
	
		//Calculations for Budget
		temp = budgetCal(temp);
		
		//Save Budget
		budgetRepository.save(temp);
		logger.info("*Service* Updated Budget: " + temp.getName());
		
		//Update Project
		projectService.update(temp.getProject());
	}
	
	/*
	 * Delete Budget
	 */
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public void delete(int id) {

		//Temp Budget
		Budget temp = budgetRepository.getOne(id);
		
		//Delete File
		budgetRepository.delete(temp);
		logger.info("*Service* Deleted Budget "+ temp.getName()+" from Project: " + temp.getProject().getName());
		
		//Update Project
		projectService.update(temp.getProject());
		
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
		double quoteSpent=0;
		double quotePending=0;
		double quoteStaged=0;
		
		//Calculate Quote Amounts
		if (budget.getQuotes() != null) {
			for (Quote quote : budget.getQuotes()){
				switch(quote.getStatus().toLowerCase()) {
					case "paid" :
						quoteSpent += quote.getCapex();
						break;
					case "pending" :
						quotePending += quote.getCapex();
						break;
					case "staged" :
						quoteStaged += quote.getCapex();
						break;
					default :
						break;
				}
			}
			
		}
		
		//Save Calculations to Budget
		budget.setQuoteSpent(quoteSpent);
		budget.setQuotePending(quotePending);
		budget.setQuoteStaged(quoteStaged);
		
		return budget;
	}
	
	/*
	 * Calculate Budget amounts for Quarters
	 */
	public Budget budgetCal(Budget budget) {
		
		//Budget Calculation Variables
		double budgetRemaining;
		double budgetRequested;
		double budgetApproved = 0;
		
		//Calculate Requested Amount
		if (budget.isEnabledQ1()) {
			budgetApproved += budget.getQ1();
		}
		if (budget.isEnabledQ2()) {
			budgetApproved += budget.getQ2();
		}
		if (budget.isEnabledQ3()) {
			budgetApproved += budget.getQ3();
		}
		if (budget.isEnabledQ4()) {
			budgetApproved += budget.getQ4();
		}
		
		budgetRequested = budget.getQ1()+budget.getQ2() + budget.getQ3() + budget.getQ4();
		budgetRemaining = budgetApproved - budget.getQuoteSpent();
		
		//Save Calculation to Budget
		budget.setBudgetRequested(budgetRequested);
		budget.setBudgetApproved(budgetApproved);
		budget.setBudgetRemaining(budgetRemaining);
		
		return budget;
	}
	
	
	
}

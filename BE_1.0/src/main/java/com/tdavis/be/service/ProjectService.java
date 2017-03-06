package com.tdavis.be.service;


import javax.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import org.springframework.security.access.prepost.PreAuthorize;

import com.tdavis.be.entity.Budget;
import com.tdavis.be.entity.FileUpload;
import com.tdavis.be.entity.Project;
import com.tdavis.be.entity.Quote;
import com.tdavis.be.repository.ProjectRepository;


@Service
@Transactional
public class ProjectService {
		
	//Log output to console
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	//Setup Date Format
	private static final DateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
	
	//STATIC Page Size
	private static final int PAGE_SIZE = 20;
	
	@Autowired
	private ProjectRepository projectRepository;
	
	@Autowired
	private BudgetService budgetService;

	/***************************************************************************************************************************
	 * 
	 * Recall Data from Repository 
	 * 
	 ***************************************************************************************************************************/
	
	/* Recall Data
	 * Project findAll
	 */
	public Iterable<Project> findAll () {
		
		return projectRepository.findAll();
	}
	
	/* Recall Data
	 * Project findById
	 */
	public Project findById(int id){
		return projectRepository.findById(id);
	}
	
	/* Recall Data
	 * Project getPrjectByYear per PAGE_SIZE
	 */
	public Page<Project> getProjectByYear(Integer pageNumber){
		PageRequest pageable = new PageRequest(pageNumber -1, PAGE_SIZE, Sort.Direction.DESC, "Year");
		return projectRepository.findAll(pageable);
	}
	
	/* Recall Data
	 * Project getPrjectByStatus per PAGE_SIZE
	 */
	public Page<Project> getProjectByStatus(String status, Integer pageNumber) {
		PageRequest pageable = new PageRequest(pageNumber -1, PAGE_SIZE, Sort.Direction.DESC, "Year");
		return projectRepository.findByStatus(pageable,status);
	}
	
	/* Recall Data
	 *  Find Number of quotes/budgets by Project
	 */
	public List<Integer> findNumbers(int id) {
		
		int files = 0;
		int quotes = 0;
		int budgets = 0;
		Project project = projectRepository.findById(id);
		List<Integer> values = new ArrayList<>();
		
		for (Budget budget : project.getBudgets()) {
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
			budgets++;
		}
		
		values.add(files);
		values.add(quotes);
		values.add(budgets);
		
		return values;
	}
	
	/***************************************************************************************************************************
	 * 
	 * Interacting With Repository 
	 * 
	 ***************************************************************************************************************************/
	
	/*
	 * Save Project
	 */
	public void save(Project project) {
			
		//Set Current Time for Timestamp
    	String time = sdf.format(new Date());
    	
    	//If...Existing Project...Update Project in Repository
		if (project.getId() != null){
			
			//Not sure why I have to do this????
			project.setDateCreated(findById(project.getId()).getDateCreated());
			
			//Temp Project
			Project temp;
			temp = budgetCal(project);

			//Set Edited timestamp
			temp.setDateEdited(time);
			
			//Update Project in Repository
			projectRepository.save(temp);
			logger.info("*Service* Updated Project: " + temp.getName());

		//Else...New Record...Save to Repository
		} else {

			//Set Created Timestamp
			project.setDateCreated(time);
			
			//Save Project to Repository
			projectRepository.save(project);
			logger.info("*Service* Saved Project "+ project.getName());
		}
	}
	
	/*
	 * Refresh Project
	 */
	public void refresh() {
		
		//Refresh Budgets
		budgetService.refresh();
		
		for (Project project : findAll()) {
			save(project);
		}
		logger.info("*Service* Refreshing Projects!");
	}
	
	/*
	 * Delete Project
	 */
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public void delete(int id) {
		Project temp = projectRepository.findById(id);
		projectRepository.delete(temp);
		logger.info("*Service* Deleted Project "+ temp.getName());
			
	}
	
	/***************************************************************************************************************************
	 * 
	 * Utility Functions
	 * 
	 ***************************************************************************************************************************/
	
	/*
	 * Calculate Budget for Project
	 */
	public Project budgetCal(Project project) {
		//Requested/Approved/Spent/Pending/Staged Amount
		double budgetRequested = 0;
		double budgetApproved = 0;
		double budgetSpent = 0;
		double budgetPending = 0;
		double budgetStaged = 0;
		
		if (project.getBudgets() != null){
			for (Budget budget : project.getBudgets()){
				budgetRequested += budget.getBudgetRequested();
				budgetApproved += budget.getBudgetApproved();
				budgetSpent += budget.getQuoteSpent();
				budgetPending += budget.getQuotePending();
				budgetStaged += budget.getQuoteStaged();
			}
		
			project.setBudgetApproved(budgetApproved);
			project.setBudgetRequested(budgetRequested);
			project.setBudgetSpent(budgetSpent);
			project.setBudgetStaged(budgetStaged);
			project.setBudgetPending(budgetPending);
		}
		
		switch (project.getStatus()){
			case "Planning" :
			case "Closed" : project.setEnabled(false);
			break;
			case "Open" : project.setEnabled(true);
			break;
			default:
			break;
		}
		
		return project;
	}

		
}

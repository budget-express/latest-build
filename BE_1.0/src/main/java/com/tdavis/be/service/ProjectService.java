package com.tdavis.be.service;


import javax.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
	
	//STATIC Page Size
	private static final int PAGE_SIZE = 20;
	
	@Autowired
	private ProjectRepository projectRepository;
	
	@Autowired
	private BudgetService budgetService;
	
	@Autowired
	private HistoryService logger;

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
		PageRequest pageable = new PageRequest(pageNumber -1, PAGE_SIZE, Sort.Direction.DESC, "Status");
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
	
	/* Recall Data
	 *  Find Number of Projects
	 */
	public Integer getCount() {
		int size = 0;
		for(@SuppressWarnings("unused") Project i: projectRepository.findAll()) {
		   size++;
		}
		
		return size;
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
    	Date time = new Date();
    	
    	//If...Existing Project...Update Project in Repository
		if (project.getId() != null){
			
			//Preserve Date Created and Created By and Budget Reference
			project.setDateCreated(projectRepository.getOne(project.getId()).getDateCreated());
			project.setCreatedBy(projectRepository.getOne(project.getId()).getCreatedBy());
			project.setBudgets(findById(project.getId()).getBudgets());
			
			//Project Calculations and Active
			project = budgetCal(project);
			project = active(project);
			
			//Set Edited Date and Edited By
			project.setDateEdited(time);
			project.setEditedBy(logger.getLoggedon());
			
			//Update Project in Repository
			projectRepository.save(project);
			logger.info("project", project.getId(), "Updated Project: " + project.getName());

		//Else...New Record...Save to Repository
		} else {

			//Set Created Date and Created By
			project.setDateCreated(time);
			project.setCreatedBy(logger.getLoggedon());
			
			//Project Active
			project = active(project);
			
			//Save Project to Repository
			projectRepository.save(project);
			logger.system("Saved Project: "+project.getName());
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
		logger.system("Refreshing Projects!");
	}
	
	/*
	 * Delete Project
	 */
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public void delete(int id) {
		Project temp = projectRepository.findById(id);
		projectRepository.delete(temp);
		logger.warning("project", temp.getId(), "Deleted Project "+ temp.getName());
			
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
		double budgetSpentOpex = 0;
		double budgetPending = 0;
		double budgetPendingOpex = 0;
		double budgetStaged = 0;
		double budgetStagedOpex = 0;
		
		if (project.getBudgets() != null){
			for (Budget budget : project.getBudgets()){
				budgetRequested += budget.getBudgetRequested();
				budgetApproved += budget.getBudgetApproved();
				
				budgetSpent += budget.getQuoteSpent();
				budgetPending += budget.getQuotePending();
				budgetStaged += budget.getQuoteStaged();
				
				budgetSpentOpex += budget.getQuoteSpentOpex();
				budgetPendingOpex += budget.getQuotePendingOpex();
				budgetStagedOpex += budget.getQuoteStagedOpex();
			}
		
			project.setBudgetApproved(budgetApproved);
			project.setBudgetRequested(budgetRequested);
			
			project.setBudgetSpent(budgetSpent);
			project.setBudgetStaged(budgetStaged);
			project.setBudgetPending(budgetPending);
			
			project.setBudgetSpentOpex(budgetSpentOpex);
			project.setBudgetStagedOpex(budgetStagedOpex);
			project.setBudgetPendingOpex(budgetPendingOpex);
		}
				
		logger.system("Calculations on Project: " + project.getName());
		return project;
	}
	
	public Project active(Project project) {
		
		switch(project.getStatus().toLowerCase()) {
		case "open" :
			project.setDateEnabled(new Date());
			project.setEnabledBy(logger.getLoggedon());
			break;
		case "closed" :
			project.setDateEnabled(projectRepository.getOne(project.getId()).getDateEnabled());
			project.setEnabledBy(projectRepository.getOne(project.getId()).getEnabledBy());
			project.setDateDisabled(new Date());
			project.setDisabledBy(logger.getLoggedon());
			break;
		case "planning" :
		default :
			break;
		}
		
		return project;
	}
	
	public double getPercentSpent(Project project) {
		
		double spent;
		
		if (project.getBudgetApproved() != 0) {
			spent = (project.getBudgetSpent()/project.getBudgetApproved())*100;
		} else {
			spent = 0;
		}
		
		return spent;
	}
	
	public double getPercentPending(Project project) {
		
		double pending;
		
		if (project.getBudgetApproved() != 0) {
			pending = ((project.getBudgetPending()+project.getBudgetStaged())/project.getBudgetApproved())*100;
		} else {
			pending = 0;
		}
		
		return pending;
	}

	public double getPercentRemaining(Project project) {
		
		double pending;
		double spent;
		double remaining;
		
		if (project.getBudgetApproved() != 0) {
			pending = ((project.getBudgetPending()+project.getBudgetStaged())/project.getBudgetApproved())*100;
			spent = (project.getBudgetSpent()/project.getBudgetApproved())*100;
			remaining = 100 - (pending+spent);
		} else {
			remaining = 0;
		}
		
		return remaining;
	}
		
}

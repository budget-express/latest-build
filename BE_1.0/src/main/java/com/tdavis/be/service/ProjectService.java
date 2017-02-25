package com.tdavis.be.service;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import com.tdavis.be.entity.Budget;
import com.tdavis.be.entity.Project;
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
	 * Project getPrjectbyYear per PAGE_SIZE
	 */
	public Page<Project> getProjectbyYear(Integer pageNumber){
		PageRequest pageable = new PageRequest(pageNumber -1, PAGE_SIZE, Sort.Direction.DESC, "Year");
		return projectRepository.findAll(pageable);
	}
	
	/*
	 * Save Project
	 */
	public void save(Project project) {
		
		//Set Created Timestamp
    	String time = sdf.format(new Date());
		if (project.getDateCreated() == null){
			
			project.setDateCreated(time);
		}

		//Save Project to Repository
		projectRepository.save(project);
		logger.info("*Service* Saved Project "+ project.getName());
	}
	
	/*
	 * Refresh Project
	 */
	public void refresh() {
		
		//Refresh Budgets
		budgetService.refresh();
		
		for (Project project : findAll()) {
			update(project);
		}
		logger.info("*Service* Refreshing Projects!");
	}
	
	/*
	 * Update Project
	 */
	public void update(Project project) {
		
		//Temp Project
		Project temp = budgetCal(project);

		//Set Edited timestamp
		String time = sdf.format(new Date());
		temp.setDateEdited(time);

		projectRepository.save(project);
		logger.info("*Service* Updated Project: " + temp.getName());
	}
	
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
	
	/*
	 * Delete Project
	 */
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public void delete(int id) {
		Project temp = projectRepository.findById(id);
		projectRepository.delete(temp);
		logger.info("*Service* Deleted Project "+ temp.getName());
			
	}
		
}

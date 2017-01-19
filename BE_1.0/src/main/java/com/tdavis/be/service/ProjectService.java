package com.tdavis.be.service;

import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tdavis.be.entity.Budget;
import com.tdavis.be.entity.Project;
import com.tdavis.be.repository.ProjectRepository;

@Service
@Transactional
public class ProjectService {
		
		private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
		@Autowired
		private ProjectRepository projectRepository;
		
		public Iterable<Project> findAll () {
			
			return projectRepository.findAll();
		}
		
		public Project findById(int id){
			return projectRepository.findById(id);
		}
		
		public void save(Project project) {

			logger.info("Create Budget");
			//Requested/Approved/Spent/Pending/Staged Amount
			double requested_budget=0;
			double approved_budget=0;
			double spent_budget=0;
			double pending_budget=0;
			double staged_budget=0;
			
			for (Budget budget : project.getBudgets()){
				requested_budget += budget.getRequested_amount();
				approved_budget += budget.getApproved_amount();
				spent_budget += budget.getQuote_spent();
				pending_budget += budget.getQuote_pending();
				staged_budget += budget.getQuote_staged();
			}
			logger.info("Calculated amounts");
			project.setAproved_budget(approved_budget);
			project.setRequested_budget(requested_budget);
			project.setSpent_budget(spent_budget);
			project.setStaged_budget(staged_budget);
			project.setPending_budget(pending_budget);
			

			projectRepository.save(project);
		}
		
		public void refresh() {

			for (Project project : findAll()) {
				save(project);
			}
		}
		
}

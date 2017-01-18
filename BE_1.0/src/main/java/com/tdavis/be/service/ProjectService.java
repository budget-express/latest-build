package com.tdavis.be.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tdavis.be.entity.Budget;
import com.tdavis.be.entity.Project;
import com.tdavis.be.repository.ProjectRepository;

@Service
@Transactional
public class ProjectService {

		@Autowired
		private ProjectRepository projectRepository;
		
		public Iterable<Project> findAll () {
			
			return projectRepository.findAll();
		}
		
		public Project findById(int id){
			return projectRepository.findById(id);
		}
		
		public void save(Project project) {
			
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
			
			project.setAproved_budget(approved_budget);
			project.setRequested_budget(requested_budget);
			project.setSpent_budget(spent_budget);
			project.setStaged_budget(staged_budget);
			project.setPending_budget(pending_budget);
			

			projectRepository.save(project);
		}
		
}

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
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.tdavis.be.entity.Budget;
import com.tdavis.be.entity.History;
import com.tdavis.be.entity.Project;
import com.tdavis.be.repository.HistoryRepository;
import com.tdavis.be.repository.ProjectRepository;
import com.tdavis.be.repository.UserRepository;

@Service
@Transactional
public class ProjectService {
		
		private final Logger logger = LoggerFactory.getLogger(this.getClass());
		
		private static final DateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		
		private static final int PAGE_SIZE = 20;
		
	
		@Autowired
		private ProjectRepository projectRepository;
		
		@Autowired
		private UserRepository userRepository;
		
		@Autowired
		private HistoryRepository historyRepository;
		
		public Iterable<Project> findAll () {
			
			return projectRepository.findAll();
		}
		
		public Project findById(int id){
			return projectRepository.findById(id);
		}
		
		public Page<Project> getProjectbyYear(Integer pageNumber){
			PageRequest pageable = new PageRequest(pageNumber -1, PAGE_SIZE, Sort.Direction.DESC, "Year");
			return projectRepository.findAll(pageable);
		}
		
		public void save(Project project) {
			
			History history = new History();
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    	String name = auth.getName(); //get logged in username
			
	    	String time = sdf.format(new Date());
	    	
			history.setName("Adding Project");
			history.setType("success");
			history.setLog("Adding Project: "+project.getName() +" By user: "+name);
			history.setUser(userRepository.findByName(name));
			history.setDate(time);
			history.setProject(project);
			
			
			//Requested/Approved/Spent/Pending/Staged Amount
			double requested_budget=0;
			double approved_budget=0;
			double spent_budget=0;
			double pending_budget=0;
			double staged_budget=0;
			
			if (project.getBudgets() != null){
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
			}
			
			
			
			switch (project.getStatus()){
				case "Planning" : project.setEnabled(false);break;
				case "Open" : project.setEnabled(true);break;
				case "Closed" : project.setEnabled(false);break;
			}
			
			if (project.getCreated() == null){
				
				project.setCreated(time);
			}

			project.setEdited(time);
			
			historyRepository.save(history);
			projectRepository.save(project);
		}
		
		public void refresh() {

			for (Project project : findAll()) {
				save(project);
			}
		}
		
		@PreAuthorize("hasRole('ROLE_ADMIN')")
		public void delete(int id) {
			projectRepository.delete(findById(id));
			
		}
		
}

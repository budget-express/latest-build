package com.tdavis.be.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.tdavis.be.entity.Budget;
import com.tdavis.be.entity.History;
import com.tdavis.be.entity.Project;
import com.tdavis.be.entity.Quote;
import com.tdavis.be.repository.BudgetRepository;
import com.tdavis.be.repository.HistoryRepository;
import com.tdavis.be.repository.ProjectRepository;
import com.tdavis.be.repository.UserRepository;

@Service
@Transactional
public class BudgetService {
		
		private final Logger logger = LoggerFactory.getLogger(this.getClass());
		
		private static final DateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
	
		@Autowired
		private BudgetRepository budgetRepository;
		
		@Autowired
		private UserRepository userRepository;
		
		@Autowired
		private HistoryRepository historyRepository;
		
		@Autowired
		private ProjectRepository projectRepository;
		
		@Autowired
		private ProjectService projectService;
		
		public Iterable<Budget> findAll () {
			
			return budgetRepository.findAll();
		}
		
		public void save(Budget budget) {
			History history = new History();
			Project project = projectRepository.findById(budget.getProject().getId());
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    	String name = auth.getName(); //get logged in username
			
	    	String time = sdf.format(new Date());
	    	
			history.setName("Adding Project");
			history.setType("success");
			history.setLog("Adding Budget: "+budget.getName() +" By user: "+name);
			history.setUser(userRepository.findByName(name));
			history.setDate(time);
			history.setProject(project);
			
			
			//Amount Spent/Pending/Staged
			double quote_spent=0;
			double quote_pending=0;
			double quote_staged=0;
			double remaining_budget=0;
			double requested_amount=0;
			
			
			
			if (budget.getQuotes() != null) {
				for (Quote quote : budget.getQuotes()){
					switch(quote.getStatus().toLowerCase()) {
						case "complete" :
							quote_spent += quote.getCapex();
							break;
						case "pending" :
							quote_pending += quote.getCapex();
							break;
						case "staged" :
							quote_staged += quote.getCapex();
							break;
					}
				}
			}
						
			budget.setQuote_spent(quote_spent);
			budget.setQuote_pending(quote_pending);
			budget.setQuote_staged(quote_staged);

			//Approved Amount
			double approved_amount=0;
			if (budget.isQ1_enabled()) {approved_amount += budget.getQ1();}
			if (budget.isQ2_enabled()) {approved_amount += budget.getQ2();}
			if (budget.isQ3_enabled()) {approved_amount += budget.getQ3();}
			if (budget.isQ4_enabled()) {approved_amount += budget.getQ4();}
			
			requested_amount = budget.getQ1()+budget.getQ2() + budget.getQ3() + budget.getQ4();
			budget.setRequested_amount(requested_amount);

			budget.setApproved_amount(approved_amount);
			
			remaining_budget = approved_amount - quote_spent;
			
			budget.setRemaining_budget(remaining_budget);
	
			if (budget.getCreated() == null){
				budget.setCreated(time);
			}
			
			budget.setEdited(time);			
			
			budget.setProject(project);
			budgetRepository.save(budget);
			projectService.save(project);
			historyRepository.save(history);
			logger.info("Saved Budget");
		}
		
		public Budget findById(int id){
			return budgetRepository.findById(id);
		}
		
		public void refresh() {
			
			for (Budget budget : findAll()) {
				save(budget);
			}
		}
		
		@PreAuthorize("hasRole('ROLE_ADMIN')")
		public void delete(int id) {
			budgetRepository.delete(findById(id));
			
		}
		
}

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
			String time = sdf.format(new Date());
			Project project = projectRepository.findById(budget.getProject().getId());
			logger.info("Project ID: " + budget.getProject().getId());
			if (budget.getId() != null) {logger.info("Budget ID: " + budget.getId());}
			/*History history = new History();
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    	String name = auth.getName(); //get logged in username
				    	
			history.setName("Adding Project");
			history.setType("success");
			history.setLog("Adding Budget: "+budget.getName() +" By user: "+name);
			history.setUser(userRepository.findByName(name));
			history.setDate(time);
			history.setProject(project);*/
			
			
			//Amount Spent/Pending/Staged
			double quoteSpent=0;
			double quotePending=0;
			double quoteStaged=0;
			double budgetRemaining=0;
			double budgetRequested=0;
			double budgetApproved=0;
			
			
			if (budget.getQuotes() != null) {
				for (Quote quote : budget.getQuotes()){
					switch(quote.getStatus().toLowerCase()) {
						case "complete" :
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
						
			budget.setQuoteSpent(quoteSpent);
			budget.setQuotePending(quotePending);
			budget.setQuoteStaged(quoteStaged);

			//Approved Amount
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
			budget.setBudgetRequested(budgetRequested);

			budget.setBudgetApproved(budgetApproved);
			
			budgetRemaining = budgetApproved - quoteSpent;
			
			budget.setBudgetRemaining(budgetRemaining);
	
			if (budget.getDateCreated() == null){
				budget.setDateCreated(time);
			}
			
			budget.setDateEdited(time);			
			
			//budget.setProject(project);
			budgetRepository.save(budget);
			projectService.save(project);
			//historyRepository.save(history);
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

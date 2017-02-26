package com.tdavis.be.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tdavis.be.entity.Budget;
import com.tdavis.be.entity.Quote;
import com.tdavis.be.repository.QuoteRepository;


@Service
@Transactional
public class QuoteService {
	
	//Log output to console
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	//Setup Date Format
	private static final DateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		
		@Autowired
		private QuoteRepository quoteRepository; 
		
		@Autowired
		private BudgetService budgetService;
		
		/***************************************************************************************************************************
		 * 
		 * Recall Data from Repository 
		 * 
		 ***************************************************************************************************************************/
		
		/* Recall Data - Quote
		 * Quote findAll
		 */
		public Iterable<Quote> findAll () {
			
			return quoteRepository.findAll();
		}
		
		/* Recall Data - Quote
		* Quote findById
		*/
		public Quote findById(int id) {
			return quoteRepository.findById(id);
		}
		
		/***************************************************************************************************************************
		 * 
		 * Interacting With Repository 
		 * 
		 ***************************************************************************************************************************/
		
		/*
		 * Save/Update Quote + Update Budget
		 */
		public void save(Quote quote) {
			
			//Find Budget
			Budget budget = budgetService.findById(quote.getBudget().getId());
			
			//Temp Quote
			Quote temp = quote;
			
			//Set Current Time for Timestamp
			String time = sdf.format(new Date());
			
			//If...Existing Quote...Update Quote in Repository
			if (temp.getId() != null){
				
				//!!!!!!Not sure why I have to do this????
				temp.setDateCreated(findById(quote.getId()).getDateCreated());
				
				//Set Edited Timestamp
				temp.setDateEdited(time);
				
				//Save Quote to Repository
				quoteRepository.save(temp);
				logger.info("*Service* Updated Quote: " + temp.getName());
				
				//Set Parent Budget
				temp.setBudget(budget);
				
				//Update Budget
				budgetService.save(budget);
				
			} else {
				//Set Created Timestamp
				temp.setDateCreated(time);
				
				//Set Parent Budget
				temp.setBudget(budget);
				
				//Save Quote to Repository
				quoteRepository.save(temp);
				logger.info("*Service* Saved Quote: " + temp.getName()+" to Budget: " + budget.getName());
				
				//Update Budget
				budgetService.save(budget);
			}
		}
			
		/*
		 * Delete Quote
		 */
		public void delete(int id) {
			
			//Temp Quote
			Quote temp = findById(id);
			
			//Delete Quote from Repository
			quoteRepository.delete(temp);
			logger.info("*Service* Deleted Quote "+ temp.getName()+" from Budget: " + temp.getBudget().getName());
			
			//Update Budget
			budgetService.save(temp.getBudget());
		}
		
		/*
		 * Refresh Quote
		 */
		public void refresh() {
			for (Quote quote : findAll()) {
				save(quote);
			}
			logger.info("*Service* Refreshing Quotes!");
		}
}

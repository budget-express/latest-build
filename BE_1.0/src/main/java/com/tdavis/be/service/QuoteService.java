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
		 * Save Quote + Update Budget
		 */
		public void save(Quote quote) {
			
			//Find Budget
			Budget budget = budgetService.findById(quote.getBudget().getId());
			
			//Set Created Timestamp
			String time = sdf.format(new Date());
			if (quote.getDateCreated() == null){
				quote.setDateCreated(time);
			}

			//Set Parent Budget
			quote.setBudget(budget);
			
			//Save Quote to Repository
			quoteRepository.save(quote);
			logger.info("*Service* Saved Quote: " + quote.getName()+" to Budget: " + budget.getName());
			
			//Update Budget
			budgetService.update(quote.getBudget());

		}
		
		/*
		 * Update Quote
		 */
		public void update(Quote quote) {
			
			//Temp Quote
			Quote temp = quote;
			
			//Set Edited Timestamp
			String time = sdf.format(new Date());
			temp.setDateEdited(time);
					
			//Save Quote to Repository
			quoteRepository.save(quote);
			logger.info("*Service* Updated Quote: " + temp.getName());
			
			//Update Budget
			budgetService.update(quote.getBudget());
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
			budgetService.update(temp.getBudget());
		}
		
		/*
		 * Refresh Quote
		 */
		public void refresh() {
			for (Quote quote : findAll()) {
				update(quote);
			}
			logger.info("*Service* Refreshing Quotes!");
		}
}

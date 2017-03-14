package com.tdavis.be.service;

import java.util.Date;

import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tdavis.be.entity.Budget;
import com.tdavis.be.entity.FileUpload;
import com.tdavis.be.entity.Quote;
import com.tdavis.be.repository.QuoteRepository;


@Service
@Transactional
public class QuoteService {
		
		@Autowired
		private QuoteRepository quoteRepository; 
		
		@Autowired
		private BudgetService budgetService;
		
		@Autowired
		private HistoryService logger;
		
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
			return quoteRepository.getOne(id);
		}
		
		/* Recall Data - Quote
		* Quote findById
		*/
		public int findNumbers(int id) {
			
			int files = 0;

			Quote quote = quoteRepository.findById(id);

				if (quote.getId() != null) {
					for (FileUpload file : quote.getFileUploads()){
						if (file.getId() != null) {
							files++;
						}
					}
				}
			
			return files;
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
			
			//Temp Quote, Message
			Quote temp = active(quote);
			
			String message;
			
			//If...Existing Quote...Update Quote in Repository
			if (temp.getId() != null){
				
				//Preserve
				temp.setDateCreated(findById(quote.getId()).getDateCreated());
				temp.setCreatedBy(quoteRepository.getOne(quote.getId()).getCreatedBy());
				temp.setBudget(quoteRepository.getOne(quote.getId()).getBudget());
				
				//Set Edited Timestamp
				temp.setDateEdited(new Date());
				temp.setEditedBy(logger.getLoggedon());

				message = "Updated Quote: " + temp.getName();
				
				//Update Budget
				budgetService.save(budget);
				
			} else {
				//Set Date Created and Created By and Budget
				temp.setDateCreated(new Date());
				temp.setCreatedBy(logger.getLoggedon());
				temp.setBudget(budget);
				
				message = "Saved Quote: " + temp.getName()+" to Budget: " + budget.getName();
			}
			
			//Save Quote to Repository
			quoteRepository.save(temp);
			logger.info("budget", temp.getBudget().getId(), message);
			
			//Update Budget
			budgetService.save(budget);
		}
			
		/*
		 * Delete Quote
		 */
		public void delete(int id) {
			
			//Temp Quote
			Quote temp = findById(id);
			
			//Delete Quote from Repository
			quoteRepository.delete(temp);
			logger.warning("budget", temp.getBudget().getId(), "Deleted Quote "+ temp.getName()+" from Budget: " + temp.getBudget().getName());
			
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
			logger.system("Refreshing Quotes!");
		}

		/*
		 * Active Quotes?
		 */
		private Quote active(Quote quote) {
			switch(quote.getStatus().toLowerCase()) {
			case "paid" :
			case "pending" :
				quote.setDateEnabled(new Date());
				quote.setEnabledBy(logger.getLoggedon());
				break;
			case "canceled" :
				quote.setDateEnabled(quoteRepository.getOne(quote.getId()).getDateEnabled());
				quote.setEnabledBy(quoteRepository.getOne(quote.getId()).getEnabledBy());
				quote.setDateDisabled(new Date());
				quote.setDisabledBy(logger.getLoggedon());
				break;
			case "staged" :
			default :
				break;
			}
			return quote;
		}
}

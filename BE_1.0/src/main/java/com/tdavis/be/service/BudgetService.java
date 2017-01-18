package com.tdavis.be.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tdavis.be.entity.Budget;
import com.tdavis.be.entity.Quote;
import com.tdavis.be.repository.BudgetRepository;

@Service
@Transactional
public class BudgetService {

		@Autowired
		private BudgetRepository budgetRepository;
		
		public Iterable<Budget> findAll () {
			
			return budgetRepository.findAll();
		}
		
		public void save(Budget budget) {
			
			//Amount Spent/Pending/Staged
			double quote_spent=0;
			double quote_pending=0;
			double quote_staged=0;

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
			
			budget.setQuote_spent(quote_spent);
			budget.setQuote_pending(quote_pending);
			budget.setQuote_staged(quote_staged);
			
			//Approved Amount
			double approved_amount=0;
			if (budget.isQ1_enabled()) {approved_amount += budget.getQ1();}
			if (budget.isQ2_enabled()) {approved_amount += budget.getQ2();}
			if (budget.isQ3_enabled()) {approved_amount += budget.getQ3();}
			if (budget.isQ4_enabled()) {approved_amount += budget.getQ4();}
			
			budget.setApproved_amount(approved_amount);
			
			budgetRepository.save(budget);
		}
		
		public Budget findById(int id){
			return budgetRepository.findById(id);
		}
		
}

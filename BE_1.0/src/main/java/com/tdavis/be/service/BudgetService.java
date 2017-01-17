package com.tdavis.be.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tdavis.be.entity.Budget;
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
			

			budgetRepository.save(budget);
		}
		
		public Budget findById(int id){
			return budgetRepository.findById(id);
		}
		
}

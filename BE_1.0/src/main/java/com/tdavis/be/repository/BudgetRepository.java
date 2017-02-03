package com.tdavis.be.repository;



import org.springframework.data.jpa.repository.JpaRepository;

import com.tdavis.be.entity.Budget;

public interface BudgetRepository extends JpaRepository<Budget, Integer> {
	Budget findByName(String name);
	
	Budget findById(Integer id);
}

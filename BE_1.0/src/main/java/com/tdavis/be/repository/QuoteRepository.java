package com.tdavis.be.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tdavis.be.entity.Quote;

public interface QuoteRepository extends JpaRepository<Quote, Integer> {
	Quote findByName(String name);
	
	Quote findById(int id);

}

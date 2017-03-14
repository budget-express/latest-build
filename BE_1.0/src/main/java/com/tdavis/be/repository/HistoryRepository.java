package com.tdavis.be.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tdavis.be.entity.History;

public interface HistoryRepository extends JpaRepository<History, Integer> {

	Iterable<History> findByUserId(Integer id);
	
}

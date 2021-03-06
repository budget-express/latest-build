package com.tdavis.be.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.tdavis.be.entity.Project;

public interface ProjectRepository extends JpaRepository<Project, Integer> {
	Project findByName(String name);
	
	Project findById(Integer id);

	Page<Project> findByStatus(Pageable page,String status);
	
	
}

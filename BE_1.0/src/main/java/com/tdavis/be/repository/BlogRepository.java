package com.tdavis.be.repository;

import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.tdavis.be.entity.Blog;

public interface BlogRepository extends JpaRepository<Blog, Integer> {
	Blog findByName(String name);
	
	Blog findById(Integer id);

	Page<Blog> findByDateCreated(Pageable page,Date dateCreated);
}

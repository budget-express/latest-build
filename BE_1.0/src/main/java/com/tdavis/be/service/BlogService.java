package com.tdavis.be.service;

import java.util.Date;

import javax.transaction.Transactional;
import org.springframework.stereotype.Service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.access.prepost.PreAuthorize;

import com.tdavis.be.entity.Blog;
import com.tdavis.be.repository.BlogRepository;


@Service
@Transactional
public class BlogService {
	//STATIC Page Size
	private static final int PAGE_SIZE = 20;
	
	@Autowired
	private BlogRepository blogRepository;
	
	@Autowired
	private HistoryService logger;
	
	/***************************************************************************************************************************
	 * 
	 * Recall Data from Repository 
	 * 
	 ***************************************************************************************************************************/
	
	/* Recall Data
	 * Blog findAll
	 */
	public Iterable<Blog> findAll () {
		
		return blogRepository.findAll();
	}
	
	/* Recall Data
	 * Blog findById
	 */
	public Blog findById(int id){
		return blogRepository.findById(id);
	}
	
	/* Recall Data
	 * Blog getByDateCreated per PAGE_SIZE
	 */
	public Page<Blog> getBlogByDateCreated(Integer pageNumber) {
		PageRequest pageable = new PageRequest(pageNumber -1, PAGE_SIZE, Sort.Direction.DESC, "dateCreated");
		return blogRepository.findAll(pageable);
	}
	
	public Page<Blog> getBlogByEnabled(Integer pageNumber) {
		PageRequest pageable = new PageRequest(pageNumber -1, PAGE_SIZE, Sort.Direction.DESC, "enabled");
		return blogRepository.findAll(pageable);
	}

	public void save(Blog blog) {
		//Set Current Time for Timestamp
    	Date time = new Date();
    	
    	//If...Existing Project...Update Project in Repository
		if (blog.getId() != null){
			
			//Preserve Date Created and Created By and Budget Reference
			blog.setDateCreated(blogRepository.getOne(blog.getId()).getDateCreated());
			blog.setCreatedBy(blogRepository.getOne(blog.getId()).getCreatedBy());
			
			//Set Edited Date and Edited By
			blog.setDateEdited(time);
			blog.setEditedBy(logger.getLoggedon());
			
			//Update Blog in Repository
			blogRepository.save(blog);
			logger.info("blog", blog.getId(), "Updated Blog: " + blog.getName());

		//Else...New Record...Save to Repository
		} else {

			//Set Created Date and Created By
			blog.setDateCreated(time);
			blog.setCreatedBy(logger.getLoggedon());
					
			//Save Project to Repository
			blogRepository.save(blog);
			logger.system("Saved Blog: "+blog.getName());
		}
	}

	/*
	 * Delete Project
	 */
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public void delete(int id) {
		Blog temp = blogRepository.findById(id);
		blogRepository.delete(temp);
		logger.warning("blog", temp.getId(), "Deleted Blog "+ temp.getName());
			
	}
	
}

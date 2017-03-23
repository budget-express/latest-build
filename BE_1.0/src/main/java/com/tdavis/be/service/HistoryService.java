package com.tdavis.be.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.tdavis.be.entity.History;
import com.tdavis.be.entity.User;
import com.tdavis.be.repository.HistoryRepository;
import com.tdavis.be.repository.UserRepository;

@Service
@Transactional
public class HistoryService {
		
		//STATIC Page Size
		private static final int PAGE_SIZE = 1000;
	
		@Autowired
		private HistoryRepository historyRepository;
		
		@Autowired
		private UserRepository userRepository;
		
		/***************************************************************************************************************************
		 * 
		 * Recall Data from Repository 
		 * 
		 ***************************************************************************************************************************/
		
		/* Recall Data
		 * History findAll
		 */
		public Iterable<History> findAll () {
			return historyRepository.findAll();
		}

		/* Recall Data
		 * Sort by Date
		 */
		public Page<History> getHistoryByDate(Integer pageNumber) {
			PageRequest pageable = new PageRequest(pageNumber -1, PAGE_SIZE, Sort.Direction.DESC, "date");
			return historyRepository.findAll(pageable);
		}
		
		/* Recall Data
		 * Sort by ID
		 */
		public Page<History> getHistoryById(Integer pageNumber) {
			PageRequest pageable = new PageRequest(pageNumber -1, PAGE_SIZE, Sort.Direction.DESC, "id");
			return historyRepository.findAll(pageable);
		}
		
		/* Recall Data
		 *  Find Number of Logs
		 */
		@SuppressWarnings("unused")
		public Integer getCount() {
			int size = 0;
			for(History log : historyRepository.findAll()) {
			   size++;
			}	
			return size;
		}		
		
		/***************************************************************************************************************************
		 * 
		 * Interacting With Repository 
		 * 
		 ***************************************************************************************************************************/
		
		/*
		 * Save History
		 */
		public void save(History history) {
			historyRepository.save(history);
		}	

		/***************************************************************************************************************************
		 * 
		 * Logger Functions - info/warning/error/debug
		 * 
		 ***************************************************************************************************************************/
		/*
		 * Write Info Log System
		 */
		public void system(String message) {
			//Find logged in user
	    	//User user = userRepository.findByName(getLoggedon());
			
	    	//Log Message to Database
			History history = new History("info","system",0,null,null,message);
			
			//Save History to Database
			historyRepository.save(history);
		}
		
		/*
		 * Write Info Log
		 */
		public void info(String object, Integer objectId, String message) {
			//Find logged in user
	    	User user = userRepository.findByName(getLoggedon());
			
	    	//Log Message to Database
			History history = new History("info",object,objectId,user.getName(),user.getId(),message);
			
			//Save History to Database
			historyRepository.save(history);
		}
		
		/*
		 * Write warning Log
		 */
		public void warning(String object, Integer objectId, String message) {
			//Find logged in user
	    	User user = userRepository.findByName(getLoggedon());
			
	    	//Log Message to Database
			History history = new History("warning",object,objectId,user.getName(),user.getId(),message);
			
			//Save History to Database
			historyRepository.save(history);
		}
		
		/*
		 * Write Error Log
		 */
		public void error(String object, Integer objectId, String message, String payload) {
			//Find logged in user
	    	User user = userRepository.findByName(getLoggedon());
			
	    	//Log Message to Database
			History history = new History("error",object,objectId,user.getName(),user.getId(),message, payload);
			
			//Save History to Database
			historyRepository.save(history);
		}
		
		/*
		 * Write Debug Log
		 */
		public void debug(String object, Integer objectId, String message) {
			//Find logged in user
	    	User user = userRepository.findByName(getLoggedon());
			
	    	//Log Message to Database
			History history = new History("debug",object,objectId,user.getName(),user.getId(),message);
			
			//Save History to Database
			historyRepository.save(history);
		}

		/***************************************************************************************************************************
		 * 
		 * Logger Functions
		 * 
		 ***************************************************************************************************************************/
		
		public String getLoggedon () {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			
			return auth.getName();
		}
		


}

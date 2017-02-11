package com.tdavis.be.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.tdavis.be.entity.History;
import com.tdavis.be.entity.Project;
import com.tdavis.be.entity.Quote;
import com.tdavis.be.repository.HistoryRepository;
import com.tdavis.be.repository.QuoteRepository;
import com.tdavis.be.repository.UserRepository;

@Service
@Transactional
public class QuoteService {

		private static final DateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
	
		@Autowired
		private QuoteRepository quoteRepository;
		
		@Autowired
		private UserRepository userRepository;
		
		@Autowired
		private HistoryRepository historyRepository;
		public Iterable<Quote> findAll () {
			
			return quoteRepository.findAll();
		}
		
		public Quote findById(int id) {
			return quoteRepository.findById(id);
		}
		
		public void save(Quote quote) {
			History history = new History();
			Project project = quote.getBudget().getProject();
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
	    	String name = auth.getName(); //get logged in username
			
	    	String time = sdf.format(new Date());
	    	
			history.setName("Adding Project");
			history.setType("success");
			history.setLog("Adding Quote: "+quote.getName() +" By user: "+name);
			history.setUser(userRepository.findByName(name));
			history.setDate(time);
			history.setProject(project);
			
			
			if (quote.getCreated() == null){
				quote.setCreated(time);
			}

			quote.setEdited(time);

			quoteRepository.save(quote);
			historyRepository.save(history);
		}
		
		public void refresh() {
			for (Quote quote : findAll()) {
				save(quote);
			}
		}
}

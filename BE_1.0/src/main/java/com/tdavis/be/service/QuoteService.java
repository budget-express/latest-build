package com.tdavis.be.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tdavis.be.entity.Quote;
import com.tdavis.be.repository.QuoteRepository;

@Service
@Transactional
public class QuoteService {

		@Autowired
		private QuoteRepository quoteRepository;
		
		public Iterable<Quote> findAll () {
			
			return quoteRepository.findAll();
		}
		
		public void save(Quote quote) {
			

			quoteRepository.save(quote);
		}
}

package com.tdavis.be.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tdavis.be.entity.FileUpload;
import com.tdavis.be.entity.Quote;

import com.tdavis.be.repository.FileUploadRepository;

@Service
@Transactional
public class FileUploadService {
	
	//Log output to console
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	//Setup Date Format
	private static final DateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
	
	
	@Autowired
	private FileUploadRepository fileuploadRepository;
	
	@Autowired
	private QuoteService quoteService;
	
	/***************************************************************************************************************************
	 * 
	 * Recall Data from Repository 
	 * 
	 ***************************************************************************************************************************/
	
	/* Recall Data
	 * FileUpload findById
	 */
	public FileUpload findById(int id) {
		return fileuploadRepository.findById(id);
	}
	
	/***************************************************************************************************************************
	 * 
	 * Interact with Repository 
	 * 
	 ***************************************************************************************************************************/
	
	/*
	 * Save File
	 */
	public void save(FileUpload fileUpload) {
		
		//Find Quote
		Quote quote = quoteService.findById(fileUpload.getQuote().getId());
		
		//Set Created Timestamp
		String created = sdf.format(new Date());
		fileUpload.setDateCreated(created);
		
		//Set Parent Quote
		fileUpload.setQuote(quote);
		
		//Save File to Repository
		fileuploadRepository.save(fileUpload);
		logger.info("*Service* Saved File "+ fileUpload.getName()+" to Quote: "+ fileUpload.getQuote().getName() + " as type: " + fileUpload.getType());
	}

	/*
	 * Delete File
	 */
	public void delete(int id) {
		//Temp FileUpload
		FileUpload temp = fileuploadRepository.getOne(id);
		
		//Delete File
		fileuploadRepository.delete(temp);
		logger.info("*Service* Deleted File "+ temp.getName()+" from Quote: " + temp.getQuote().getName());
	}

}

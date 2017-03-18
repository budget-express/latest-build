package com.tdavis.be.service;

import java.util.Date;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tdavis.be.entity.FileUpload;

import com.tdavis.be.repository.FileUploadRepository;

@Service
@Transactional
public class FileUploadService {
	
	@Autowired
	private FileUploadRepository fileuploadRepository;
	
	@Autowired
	private QuoteService quoteService;

	
	@Autowired
	private HistoryService logger;
	
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
		
		//Set Date Created and Created By and Set Quote
		fileUpload.setDateCreated(new Date());
		fileUpload.setCreatedBy(logger.getLoggedon());
		fileUpload.setQuote(quoteService.findById(fileUpload.getQuote().getId()));
		
		//Save File to Repository
		fileuploadRepository.save(fileUpload);
		logger.info("quote", fileUpload.getQuote().getId(), "Saved File "+ fileUpload.getName()+" to Quote: "+ fileUpload.getQuote().getName() + " as type: " + fileUpload.getType());
	}

	/*
	 * Delete File
	 */
	public void delete(int id) {
		//Temp FileUpload
		FileUpload temp = fileuploadRepository.getOne(id);
		
		//Delete File
		fileuploadRepository.delete(temp);
		logger.warning("quote", temp.getQuote().getId(), "Deleted File "+ temp.getName()+" from Quote: " + temp.getQuote().getName());
	}

}

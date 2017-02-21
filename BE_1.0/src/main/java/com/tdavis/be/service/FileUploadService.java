package com.tdavis.be.service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

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
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private static final DateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
	
	
	@Autowired
	private FileUploadRepository fileuploadRepository;
	
	@Autowired
	private QuoteService quoteService;
	
	
	public void save(FileUpload fileUpload) {
		String created = sdf.format(new Date());
		int id = fileUpload.getQuote().getId();
		
		fileUpload.setCreated(created);
		fileuploadRepository.save(fileUpload);
		
		Quote quote = quoteService.findById(id);
		List<FileUpload> temp = quoteService.findById(id).getFileUploads();
		temp.add(fileUpload);
		quote.setFileUploads(temp);
		quoteService.save(quote);
		
		
	}


	public FileUpload findById(int id){
			return fileuploadRepository.findById(id);
		}


	public void delete(int id) {
		FileUpload temp = fileuploadRepository.getOne(id);
		fileuploadRepository.delete(temp);
	}

}

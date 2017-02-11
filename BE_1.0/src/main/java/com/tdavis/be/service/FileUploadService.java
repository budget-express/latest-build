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
import com.tdavis.be.repository.FileUploadRepository;

@Service
@Transactional
public class FileUploadService {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private static final DateFormat sdf = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
	
	@Autowired
	private FileUploadRepository fileuploadRepository;
	
	
	public void saveFile(FileUpload fileupload) {
		String created = sdf.format(new Date());
		
		fileupload.setCreated(created);
		
		fileuploadRepository.save(fileupload);
		
	}

}

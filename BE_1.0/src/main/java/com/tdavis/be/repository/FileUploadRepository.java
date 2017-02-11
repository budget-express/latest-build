package com.tdavis.be.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.tdavis.be.entity.FileUpload;

public interface FileUploadRepository extends JpaRepository<FileUpload, Integer> {
	FileUpload findByname(String fileName);
	
	FileUpload findById(Integer id);
	
	FileUpload findByType(String type);
	
	

}

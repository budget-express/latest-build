package com.tdavis.be.controller.main;


import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.tdavis.be.entity.FileUpload;
import com.tdavis.be.entity.Quote;
import com.tdavis.be.service.FileUploadService;
import com.tdavis.be.service.HistoryService;
import com.tdavis.be.service.QuoteService;



@Controller
@RequestMapping("/main/file")
public class MainFileController {

	// Define the logger object for this class
	@Autowired
	private HistoryService logger;

	//Constants
	private String redirectQuote = "redirect:/main/quote/";
	
	@Autowired
	private FileUploadService fileUploadService;
	
	@Autowired
	private QuoteService quoteService;
	
	//Access file on web form
	@ModelAttribute("file")
	public FileUpload fileConstruct() {
		return new FileUpload();
	}

	/********************************************************************************************************
	 *  /Settings/Project/Budgets - Save, Delete
	 * 	Interact with Budget Repositories - Access Database
	 * 
	 *********************************************************************************************************/
	
	/*
	 * Home >> Main >> Projects >> Budget >> Quote >> File >> Save
	 * Save File
	 */
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PostMapping("/save")
	public String saveFile(@RequestParam("myFile") MultipartFile myFile,@RequestParam("fileType") String type, String id, Model model) throws IOException {
		FileUpload fileUpload = new FileUpload();
		
		int quoteid = Integer.parseInt(id);
		Quote quote = quoteService.findById(quoteid);
		
	
		fileUpload.setName(myFile.getOriginalFilename());
		fileUpload.setType(type);
		fileUpload.setFiledata(myFile.getBytes());
		fileUpload.setQuote(quote);		
		fileUploadService.save(fileUpload);
		
		return redirectQuote + quote.getId();
	}
	
	@GetMapping("/download/{id}")
	public void getFile(@PathVariable int id, HttpServletResponse response,HttpServletRequest request) throws IOException {
		FileUpload fileUpload =  fileUploadService.findById(id);
		
		response.setHeader("Content-Disposition", "attachment;filename=\""+fileUpload.getName()+"\"");
		response.getOutputStream().write(fileUpload.getFiledata());
		response.getOutputStream().close();
	}
	
	@PostMapping("/delete")
	public boolean deleteFile (@RequestParam("id") String id) {
		
		logger.system("Deleting File:" + id);
		boolean success;		
		fileUploadService.delete(Integer.parseInt(id));
		success = true;
		return success;
	}
	
	/*@RequestMapping("/delete/{id}")
	public String deleteFile(@PathVariable int id, Model model) {
		Quote quote = fileUploadService.findById(id).getQuote();
		
		fileUploadService.delete(id);
		
		//Redirect to Quote Details
		return "redirect:/main/quote/" + quote.getId();
	}*/
}

package com.tdavis.be.controller;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.tdavis.be.entity.FileUpload;
import com.tdavis.be.entity.Quote;
import com.tdavis.be.service.FileUploadService;
import com.tdavis.be.service.QuoteService;

@Controller
@RequestMapping("/file")
public class FileUploadController {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private FileUploadService fileUploadService;
	
	@Autowired
	private QuoteService quoteService;
	
	@GetMapping("/download/{id}")
	public void getFile(@PathVariable int id, HttpServletResponse response,HttpServletRequest request) throws IOException {
		FileUpload fileUpload =  fileUploadService.findById(id);
		
		response.setHeader("Content-Disposition", "attachment;filename=\""+fileUpload.getName()+"\"");
		response.getOutputStream().write(fileUpload.getFiledata());
		response.getOutputStream().close();
	}
	
	@RequestMapping("/delete/{id}")
	public String deleteFile(@PathVariable int id, Model model) {
		Quote quote = fileUploadService.findById(id).getQuote();
		fileUploadService.delete(id);
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    	String name = auth.getName(); //get logged in username
    	model.addAttribute("username", name);
    	model.addAttribute("quote", quote);
		model.addAttribute("title", "Quote");
		
		
		return "quote-details";
	}
	
	@PostMapping()
	public String saveFile(@RequestParam("myFile") MultipartFile myFile,@RequestParam("fileType") String type, String id, Model model) throws IOException {
		FileUpload fileUpload = new FileUpload();
		Quote quote = quoteService.findById(Integer.parseInt(id));
		
		fileUpload.setName(myFile.getOriginalFilename());
		fileUpload.setType(type);
		fileUpload.setFiledata(myFile.getBytes());
		fileUpload.setQuote(quote);
				
		fileUploadService.save(fileUpload);
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    	String name = auth.getName(); //get logged in username
    	model.addAttribute("username", name);
    	model.addAttribute("quote", quoteService.findById(Integer.parseInt(id)));
		model.addAttribute("title", "Quote");
		
		return "quote-details";
	}
}

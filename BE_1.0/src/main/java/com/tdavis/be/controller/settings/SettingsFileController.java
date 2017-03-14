package com.tdavis.be.controller.settings;


import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.tdavis.be.entity.FileUpload;
import com.tdavis.be.entity.Quote;
import com.tdavis.be.service.FileUploadService;
import com.tdavis.be.service.QuoteService;



@Controller
@RequestMapping("/settings")
public class SettingsFileController {

	//Log output to console
		private final Logger logger = LoggerFactory.getLogger(this.getClass());
		
		//Constants
		private String redirectQuote = "redirect:/settings/quote/";
		
		@Autowired
		private FileUploadService fileUploadService;
		
		@Autowired
		private QuoteService quoteService;
		
		//Access budget on web form
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
		 * Home >> Settings >> Projects >> Budget >> Quote >> Save
		 * Save Project
		 */
		@PreAuthorize("hasRole('ROLE_ADMIN')")
		@PostMapping("/file/save")
		public String saveFile(@RequestParam("myFile") MultipartFile myFile,@RequestParam("fileType") String type, int id, Model model) throws IOException {
			FileUpload fileUpload = new FileUpload();
			Quote quote = quoteService.findById(id);
			
			fileUpload.setName(myFile.getOriginalFilename());
			fileUpload.setType(type);
			fileUpload.setFiledata(myFile.getBytes());
			fileUpload.setQuote(quote);
					
			fileUploadService.save(fileUpload);
			
			return redirectQuote + quote.getId();
		}
}

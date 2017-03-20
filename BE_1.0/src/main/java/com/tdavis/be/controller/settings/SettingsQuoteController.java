package com.tdavis.be.controller.settings;


import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tdavis.be.entity.FileUpload;
import com.tdavis.be.entity.Quote;
import com.tdavis.be.entity.User;
import com.tdavis.be.service.QuoteService;
import com.tdavis.be.service.UserService;



@Controller
@RequestMapping("/settings")
public class SettingsQuoteController {
	//Log output to console
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	//Constants
	private String redirectBudget = "redirect:/settings/budget/";
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private QuoteService quoteService;
	
	//Access budget on web form
	@ModelAttribute("quote")
	public Quote quoteConstruct() {
		return new Quote();
	}
	
	//Access budget on web form
	@ModelAttribute("file")
	public FileUpload fileConstruct() {
		return new FileUpload();
	}
	
	/********************************************************************************************************
	 *  /Settings/Project/Budget/Quote - List(main page), Find/Sort Quotes
	 * 	Query Quote Objects
	 * 
	 *********************************************************************************************************/
	/*
	 * Home >> Settings >> Projects >> Budget >> Quote
	 * Listing of Quote
	 */
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping("/quote/{id}")
	public String listQuotes(Model model, @PathVariable int id) {
		
		//Find Quote
		Quote quote = quoteService.findById(id);
		
		//Set Page Navigation
		List<String> navigation = new ArrayList<>();
		List<String> links = new ArrayList<>();
		
		navigation.add("Settings");
		navigation.add("Projects");
		navigation.add(quote.getBudget().getProject().getName());
		navigation.add(quote.getBudget().getName());
		navigation.add(quote.getName());
		
		links.add("settings");
		links.add("settings/projects");
		links.add("settings/project/"+quote.getBudget().getProject().getId());
		links.add("settings/budget/"+quote.getBudget().getId());

		//Find logged in user
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    	User user = userService.findByName(auth.getName());
    	    	
    	//Set Model Attributes
    	model.addAttribute("user", user);
    	model.addAttribute("quote", quote);
    	model.addAttribute("navtitle", quote.getName());
    	model.addAttribute("navigation" , navigation);
    	model.addAttribute("links" , links);
    	model.addAttribute("count" , quoteService.findNumbers(id));
		model.addAttribute("title", quote.getName());
		
		//projects.html
		return "/settings/view-quote";
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
	@PostMapping("/quote/save")
	public String saveQuote(@ModelAttribute @Valid Quote quote, BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			
			logger.debug("Error: "+bindingResult);
			return "redirect:/error";
		}

		//Call Quote Service to Save Quote to Budget
		quoteService.save(quote);
		
		//Redirect to Project Details
		return redirectBudget + quote.getBudget().getId();
	}
	
	/*
	 * Home >> Settings >> Budget >> Delete
	 * Delete Budget
	 */
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping("/quote/delete/{id}")
	@ResponseBody
	public String projectDelete(Model model, @PathVariable int id){

		//Call Quote Service to Delete Quote
		quoteService.delete(id);

		//Redirect to Projects
		return "success";
	}
}

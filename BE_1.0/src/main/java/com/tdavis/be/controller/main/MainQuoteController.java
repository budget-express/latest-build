package com.tdavis.be.controller.main;


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


import com.tdavis.be.entity.Budget;
import com.tdavis.be.entity.FileUpload;
import com.tdavis.be.entity.Project;
import com.tdavis.be.entity.Quote;
import com.tdavis.be.entity.User;
import com.tdavis.be.service.BudgetService;
import com.tdavis.be.service.HistoryService;
import com.tdavis.be.service.ProjectService;
import com.tdavis.be.service.QuoteService;
import com.tdavis.be.service.UserService;



@Controller
@RequestMapping("/main")
public class MainQuoteController {

	@Autowired
	private ProjectService projectService;	
	
	@Autowired
	private BudgetService budgetService;
	
	@Autowired
	private QuoteService quoteService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private HistoryService logger;

	//Access quote on web page form
	@ModelAttribute("quote")
	public Quote quoteConstruct() {
		return new Quote();
	}
	
	//Access file on web page form
	@ModelAttribute("file")
	public FileUpload fileConstruct() {
		return new FileUpload();
	}
	
	/********************************************************************************************************
	 *  /Settings/Projects - List(main page), Find/Sort Projects
	 * 	Query Project Objects
	 * 
	 *********************************************************************************************************/
	
	/*
	 * Home >> Settings >> Projects >> All
	 * Listing of Projects
	 */
	@PreAuthorize("hasRole('ROLE_USER')")
	@RequestMapping("/quote/{id}")
	public String viewProject(Model model, @PathVariable int id) {
		
		//Find Quote
		Quote quote = quoteService.findById(id);
		
		//Find Budget
		Budget budget = budgetService.findById(quote.getBudget().getId());
		
		//Find Project
		Project project = projectService.findById(budget.getProject().getId());

		//Set Page Navigation
		List<String> navigation = new ArrayList<>();
		
		navigation.add("Main");
		navigation.add("Projects");
		navigation.add(project.getName());
		navigation.add(budget.getName());
		navigation.add(quote.getName());
		
		//Find logged in user
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    	User user = userService.findByName(auth.getName());   	
    	
    	//Set Model Attributes
    	model.addAttribute("user", user);
    	model.addAttribute("project", project);
    	model.addAttribute("budget", budget);
    	model.addAttribute("quote", quote);
    	model.addAttribute("navigation" , navigation);
    	model.addAttribute("count", projectService.findNumbers(project.getId()));
    	model.addAttribute("percentspent", projectService.getPercentSpent(project));
    	model.addAttribute("percentpending", projectService.getPercentPending(project));
    	model.addAttribute("logs",logger.findAll());
		model.addAttribute("title", quote.getName());
		
		return "/main/view-quote";
	}
}

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
import com.tdavis.be.entity.Project;
import com.tdavis.be.entity.Quote;
import com.tdavis.be.entity.User;
import com.tdavis.be.service.BudgetService;
import com.tdavis.be.service.HistoryService;
import com.tdavis.be.service.ProjectService;
import com.tdavis.be.service.UserService;



@Controller
@RequestMapping("/main/budget")
public class MainBudgetController {
	
	@Autowired
	private ProjectService projectService;	
	
	@Autowired
	private BudgetService budgetService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private HistoryService logger;
	
	//Access budget on web page form
	@ModelAttribute("budget")
	public Budget budgetConstruct() {
		return new Budget();
	}
	
	//Access quote on web page form
	@ModelAttribute("quote")
	public Quote quoteConstruct() {
		return new Quote();
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
	@RequestMapping("/{id}")
	public String viewProject(Model model, @PathVariable int id) {
		
		//Find Budget
		Budget budget = budgetService.findById(id);
		
		//Find Project
		Project project = projectService.findById(budget.getProject().getId());

		//Set Page Navigation
		List<String> navigation = new ArrayList<>();
		List<String> links = new ArrayList<>();
		
		navigation.add("Main");
		navigation.add("Projects");
		navigation.add(project.getName());
		navigation.add(budget.getName());
		
		links.add("main");
		links.add("main/projects/open");
		links.add("main/project/"+ project.getId());
		
		//Find logged in user
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    	User user = userService.findByName(auth.getName());   	
    	
    	//Set Model Attributes
    	model.addAttribute("user", user);
    	model.addAttribute("project", project);
    	model.addAttribute("budget", budget);
    	model.addAttribute("navigation" , navigation);
    	model.addAttribute("links" , links);
    	model.addAttribute("count", projectService.findNumbers(project.getId()));
    	model.addAttribute("percentspent", projectService.getPercentSpent(project));
    	model.addAttribute("percentpending", projectService.getPercentPending(project));
    	model.addAttribute("percentremaining", projectService.getPercentRemaining(project));
    	model.addAttribute("bpercentspent", budgetService.getPercentSpent(budget));
    	model.addAttribute("bpercentpending", budgetService.getPercentPending(budget));
    	model.addAttribute("bpercentremaining", budgetService.getPercentRemaining(budget));
    	model.addAttribute("logs",logger.findAll());
		model.addAttribute("title", budget.getName());
		
		return "/main/view-budget";
	}
	
	/********************************************************************************************************
	 *  /Settings/Project/Budgets - Save, Delete
	 * 	Interact with Budget Repositories - Access Database
	 * 
	 *********************************************************************************************************/
	
	/*
	 * Home >> Settings >> Projects >> Save
	 * Save Project
	 */
	@PreAuthorize("hasRole('ROLE_USER')")
	@PostMapping("/save")
	public String saveBudget(@ModelAttribute @Valid Budget budget, BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			return "redirect:/error";
		}
	
		//Call Budget Service to Save Budget to Project
		budgetService.save(budget);
		
		//Redirect to Project Details
		return "redirect:/main/budget/" + budget.getId();
	}
	
	/*
	 * Home >> Settings >> Budget >> Delete
	 * Delete Budget
	 */
	@PreAuthorize("hasRole('ROLE_USER')")
	@RequestMapping("/delete/{id}")
	@ResponseBody
	public String projectDelete(Model model, @PathVariable int id){

		//Call Project Service to Delete Project
		budgetService.delete(id);

		//Redirect to Projects
		return "success";
	}	
	
}

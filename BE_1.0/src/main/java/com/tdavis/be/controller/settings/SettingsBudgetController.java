package com.tdavis.be.controller.settings;


import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.tdavis.be.entity.Budget;
import com.tdavis.be.entity.Project;
import com.tdavis.be.entity.Quote;
import com.tdavis.be.service.BudgetService;
import com.tdavis.be.service.ProjectService;



@Controller
@RequestMapping("/settings")
public class SettingsBudgetController {

	//Log output to console
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	//Constants
	private String redirectProject = "redirect:/settings/project/";
	private String redirectBudget = "redirect:/settings/budget/";
	
	@Autowired
	private BudgetService budgetService;
	
	//Access quote on web form
	@ModelAttribute("quote")
	public Quote quoteConstruct() {
		return new Quote();
	}
	
	//Access budget on web form
	@ModelAttribute("budget")
	public Budget budgetConstruct() {
		return new Budget();
	}
	
	/********************************************************************************************************
	 *  /Settings/Project/Budget - List(main page), Find/Sort Budgets
	 * 	Query Budget Objects
	 * 
	 *********************************************************************************************************/
	/*
	 * Home >> Settings >> Projects >> All
	 * Listing of Projects
	 */
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping("/budget/{id}")
	public String listProjects(Model model, @PathVariable int id) {
		
		//Find Budget
		Budget budget = budgetService.findById(id);
		
		//Set Page Navigation
		List<String> navigation = new ArrayList<>();
		
		navigation.add("Home");
		navigation.add("Settings");
		navigation.add("Projects");
		navigation.add(budget.getProject().getName());
		navigation.add(budget.getName());
				
		//Find logged in user
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    	String name = auth.getName(); //get logged in username
    	
    	//Set Model Attributes
    	model.addAttribute("username", name);
    	model.addAttribute("budget", budget);
    	model.addAttribute("navtitle", budget.getName());
    	model.addAttribute("navigation" , navigation);
    	model.addAttribute("count" , budgetService.findNumbers(id));
		model.addAttribute("title", budget.getName());
		
		//projects.html
		return "view-budget";
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
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PostMapping("/budget/save")
	public String saveBudget(@ModelAttribute @Valid Budget budget, BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			
			logger.debug("Error: "+bindingResult);
			return "redirect:/error";
		}
	
		//Call Budget Service to Save Budget to Project
		budgetService.save(budget);
		
		//Redirect to Project Details
		return redirectProject + budget.getProject().getId();
	}
	
	/*
	 * Home >> Settings >> Budget >> Delete
	 * Delete Budget
	 */
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping("/budget/delete/{id}")
	@ResponseBody
	public String projectDelete(Model model, @PathVariable int id){

		//Call Project Service to Delete Project
		budgetService.delete(id);

		//Redirect to Projects
		return "success";
	}	
}

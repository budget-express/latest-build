package com.tdavis.be.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tdavis.be.entity.Budget;
import com.tdavis.be.service.BudgetService;
import com.tdavis.be.service.ProjectService;

@Controller
@RequestMapping("/budget")
public class BudgetController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private BudgetService budgetService;
	
	@Autowired
	private ProjectService projectService;
	
	@ModelAttribute("budget")
	public Budget construct() {
		return new Budget();
	}
	
	@RequestMapping("/{id}")
	public String showBudget (Model model, @PathVariable String id) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    	String name = auth.getName(); //get logged in username
    	model.addAttribute("username", name);
    	model.addAttribute("budget", budgetService.findById(Integer.parseInt(id)));
		model.addAttribute("title", "Budget");
		return "budget";
	}
	
	@PostMapping()
	public String saveBudget(@ModelAttribute @Valid Budget budget, BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			
			return "project";
		}
		int id = budget.getProject().getId();
		logger.info("id= " +id);
		budgetService.save(budget);
		
		double approved = projectService.findById(id).getApproved_budget();
		double spent = projectService.findById(id).getSpent_budget();
		double balance = 0;
		
		balance = approved - spent;
		
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    	String name = auth.getName(); //get logged in username
    	model.addAttribute("username", name);
    	model.addAttribute("project", projectService.findById(id));
		model.addAttribute("title", "Projects");
		model.addAttribute("spent",balance);
		model.addAttribute("success", true);
		//Change to budget-detail when built
		return "project_details";
	}
}

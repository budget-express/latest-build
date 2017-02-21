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
import com.tdavis.be.entity.Project;
import com.tdavis.be.entity.Quote;
import com.tdavis.be.service.BudgetService;
import com.tdavis.be.service.ProjectService;

@Controller
@RequestMapping("/budget")
public class BudgetController {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private String modelBudgetTitle = "Budget";
	private String modelUsername = "username";
	private String modelBudget = "budget";
	private String modelProject = "project";
	private String modelTitle = "title";
	
	@Autowired
	private BudgetService budgetService;
	
	@Autowired
	private ProjectService projectService;
	
	@ModelAttribute("budget")
	public Budget budgetConstruct() {
		return new Budget();
	}
	
	@ModelAttribute("quote")
	public Quote quoteConstruct() {
		return new Quote();
	}
	
	@RequestMapping("/{id}")
	public String showBudget (Model model, @PathVariable int id) {
		
		double approved = budgetService.findById(id).getBudgetApproved();
		double spent = budgetService.findById(id).getQuoteSpent();
		double balance;
		
		balance = approved - spent;
		
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    	String name = auth.getName(); //get logged in username
    	model.addAttribute(modelUsername, name);
    	model.addAttribute(modelBudget, budgetService.findById(id));
    	model.addAttribute("spent",balance);
		model.addAttribute(modelTitle, modelBudgetTitle);
		return modelBudget;
	}
	
	@RequestMapping("/delete/{id}")
	public String deleteBudget (Model model, @PathVariable int id) {
		Project project = budgetService.findById(id).getProject();
		int projectId = project.getId();
		
		budgetService.delete(id);
		projectService.save(project);
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    	String name = auth.getName(); //get logged in username
    	model.addAttribute(modelUsername, name);
    	model.addAttribute(modelProject, projectService.findById(projectId));
    	model.addAttribute(modelTitle, "Project Details -" + projectService.findById(projectId).getName());
		
		
		
		return "project-details";
	}
	
	@PostMapping()
	public String saveBudget(@ModelAttribute @Valid Budget budget, BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			
			return modelProject;
		}
		int id = budget.getProject().getId();
		logger.info("id= " +id);
		budgetService.save(budget);
			
		double approved = projectService.findById(id).getBudgetApproved();
		double spent = projectService.findById(id).getBudgetSpent();
		double balance;
		
		balance = approved - spent;
		
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    	String name = auth.getName(); //get logged in username
    	model.addAttribute(modelUsername, name);
    	model.addAttribute(modelProject, projectService.findById(id));
		model.addAttribute(modelTitle, "Projects");
		model.addAttribute("spent",balance);
		model.addAttribute("success", true);
		//Change to budget-detail when built
		return "project-details";
	}
}

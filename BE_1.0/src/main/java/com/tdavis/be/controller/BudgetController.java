package com.tdavis.be.controller;

import javax.validation.Valid;

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

@Controller
@RequestMapping("/budget")
public class BudgetController {
	@Autowired
	private BudgetService budgetService;
	
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
		
		//budgetService.save(budget);
		
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    	String name = auth.getName(); //get logged in username
    	model.addAttribute("username", name);
    	model.addAttribute("projects", budgetService.findById(budget.getProject().getId()));
		model.addAttribute("title", "Projects");		
		model.addAttribute("success", true);
		//Change to budget-detail when built
		return "project-detail";
	}
}

package com.tdavis.be.controller;

import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import javax.validation.Valid;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import com.tdavis.be.entity.Budget;
import com.tdavis.be.entity.Quote;
import com.tdavis.be.service.BudgetService;

@Controller
@RequestMapping("/budget")
public class BudgetController {
	
	//private final Logger logger = LoggerFactory.getLogger(this.getClass());
	private String modelBudgetTitle = "Budget";
	private String modelUsername = "username";
	private String modelBudget = "budget";
	private String modelTitle = "title";
	
	@Autowired
	private BudgetService budgetService;
		
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
	
	/*
	 * Delete Budget
	 */
	@RequestMapping("/delete/{id}")
	public String deleteBudget (Model model, @PathVariable int id) {
		
		//Get Project ID
		int projectId = budgetService.findById(id).getProject().getId();
		
		//Call Budget Services to Delete Budget from Project
		budgetService.delete(id);

		//Redirect to Project Details
		return "redirect:/project/" + projectId;
	}
	
	/*
	 * Save Budget
	 */
	@PostMapping()
	public String saveBudget(@ModelAttribute @Valid Budget budget, BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			
			return "redirect:index";
		}
		
		//Call Budget Service to Save Budget to Project
		budgetService.save(budget);
		
		//Redirect to Project Details
		return "redirect:/project/" + budget.getProject().getId();
	}
}

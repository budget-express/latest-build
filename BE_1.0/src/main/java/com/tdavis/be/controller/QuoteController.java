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
import com.tdavis.be.entity.FileUpload;
import com.tdavis.be.entity.Project;
import com.tdavis.be.entity.Quote;
import com.tdavis.be.service.BudgetService;
import com.tdavis.be.service.QuoteService;

@Controller
@RequestMapping("/quote")
public class QuoteController {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private QuoteService quoteService;
	
	@Autowired
	private BudgetService budgetService;

	
	@ModelAttribute("quote")
	public Quote quoteConstruct() {
		return new Quote();
	}
	
	@RequestMapping("/{id}")
	public String showQuote (Model model, @PathVariable int id) {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    	String name = auth.getName(); //get logged in username
    	model.addAttribute("username", name);
    	model.addAttribute("quote", quoteService.findById(id));
		model.addAttribute("title", "Quote");
		return "quote-details";
	}
	
	@RequestMapping("/delete/{id}")
	public String deleteBudget (Model model, @PathVariable int id) {
		Budget budget = quoteService.findById(id).getBudget();
				
		quoteService.delete(id);
		budgetService.save(budget);
		
		double approved = budget.getBudgetApproved();
		double spent = budget.getQuoteSpent();
		double balance =0;
		
		balance = approved - spent;
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    	String name = auth.getName(); //get logged in username
    	model.addAttribute("username", name);
    	model.addAttribute("budget", budget);
    	model.addAttribute("spent",balance);
    	model.addAttribute("title", "Budget Details -" + budget.getName());

		return "budget";
	}
	
	@PostMapping()
	public String saveUser(@ModelAttribute @Valid Quote quote, BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			
			return "project";
		}
		
		quote.setBudget(budgetService.findById(quote.getBudget().getId()));
		quoteService.save(quote);
		
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();

    	model.addAttribute("username", auth.getName());
    	model.addAttribute("quote", quote);
		model.addAttribute("title", "Quote");		
		model.addAttribute("success", true);
		return "quote-details";
	}
}

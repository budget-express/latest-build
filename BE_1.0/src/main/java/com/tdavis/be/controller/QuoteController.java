package com.tdavis.be.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tdavis.be.entity.Quote;
import com.tdavis.be.service.BudgetService;
import com.tdavis.be.service.QuoteService;

@Controller
@RequestMapping("/quote")
public class QuoteController {
	
	@Autowired
	private QuoteService quoteService;
	
	@Autowired
	private BudgetService budgetService;
	
	@ModelAttribute("quote")
	public Quote construct() {
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
}

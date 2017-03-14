package com.tdavis.be.controller;

import javax.validation.Valid;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
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

import com.tdavis.be.entity.Quote;
import com.tdavis.be.service.QuoteService;

@Controller
@RequestMapping("/old/quote")
public class QuoteController {
	
	//private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private QuoteService quoteService;
		
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
		return "/old/quote-details";
	}
	
	@RequestMapping("/delete/{id}")
	public String deleteQuote (Model model, @PathVariable int id) {
		
		//Find Budget ID
		int budgetId = quoteService.findById(id).getBudget().getId();
		
		//Call Quote Service to Delete Quote from Budget
		quoteService.delete(id);
		
		//Redirect to Budget Details
		return "redirect:/budget/" + budgetId;
	}
	
	@PostMapping()
	public String saveQuote(@ModelAttribute @Valid Quote quote, BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			
			return "project";
		}
				
		//Call Quote Service to Save Quote to Budget
		quoteService.save(quote);
		
		//Redirect to Budget Details
		return "redirect:/budget/" + quote.getBudget().getId();
	}
}

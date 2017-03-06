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
import com.tdavis.be.service.QuoteService;



@Controller
@RequestMapping("/settings")
public class SettingsQuoteController {
	//Log output to console
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	//Constants
	private String redirectProject = "redirect:/settings/project/";
	private String redirectBudget = "redirect:/settings/budget/";
	
	@Autowired
	private QuoteService quoteService;
	
	//Access budget on web form
	@ModelAttribute("quote")
	public Quote quoteConstruct() {
		return new Quote();
	}
	
	/********************************************************************************************************
	 *  /Settings/Project/Budget/Quote - List(main page), Find/Sort Quotes
	 * 	Query Quote Objects
	 * 
	 *********************************************************************************************************/
	/*
	 * Home >> Settings >> Projects >> Budget >> Quote
	 * Listing of Quote
	 */
	
	/********************************************************************************************************
	 *  /Settings/Project/Budgets - Save, Delete
	 * 	Interact with Budget Repositories - Access Database
	 * 
	 *********************************************************************************************************/
	
	/*
	 * Home >> Settings >> Projects >> Budget >> Quote >> Save
	 * Save Project
	 */
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PostMapping("/quote/save")
	public String saveQuote(@ModelAttribute @Valid Quote quote, BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			
			logger.debug("Error: "+bindingResult);
			return "redirect:/error";
		}
	
		//Call Quote Service to Save Quote to Budget
		quoteService.save(quote);
		
		//Redirect to Project Details
		return redirectProject + quote.getBudget().getId();
	}
	
	/*
	 * Home >> Settings >> Budget >> Delete
	 * Delete Budget
	 */
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping("/quote/delete/{id}")
	@ResponseBody
	public String projectDelete(Model model, @PathVariable int id){

		//Call Quote Service to Delete Quote
		quoteService.delete(id);

		//Redirect to Projects
		return "success";
	}
}

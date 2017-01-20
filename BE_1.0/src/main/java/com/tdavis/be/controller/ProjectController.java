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

import com.tdavis.be.entity.Project;
import com.tdavis.be.service.BudgetService;
import com.tdavis.be.service.ProjectService;


@Controller
@RequestMapping("/project")
public class ProjectController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private ProjectService projectService;
	
	@Autowired
	private BudgetService budgetService;
	
	@ModelAttribute("project")
	public Project construct() {
		return new Project();
	}
	
	@RequestMapping()
	public String showProjects(Model model) {
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    	String name = auth.getName(); //get logged in username
    	model.addAttribute("username", name);
    	model.addAttribute("projects", projectService.findAll());
		model.addAttribute("title", "Projects");
		return "project";
	}
	
	@RequestMapping("/{id}")
	public String ShowProjectDetails(Model model, @PathVariable String id) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    	String name = auth.getName(); //get logged in username
    	model.addAttribute("username", name);
    	model.addAttribute("project", projectService.findById(Integer.parseInt(id)));
		model.addAttribute("title", "Project");
		return "details";
	}
	
	@RequestMapping("/details")
	public String projectDetails(Model model) {
		int id = 1;

		double approved = projectService.findById(id).getApproved_budget();
		double spent = projectService.findById(id).getSpent_budget();
		double balance = 0;
		
		balance = approved - spent;

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    	String name = auth.getName(); //get logged in username
    	model.addAttribute("username", name);
    	model.addAttribute("project", projectService.findById(id));
    	model.addAttribute("spent",balance);
		model.addAttribute("title", "Project Details -" + projectService.findById(id).getName());
		return "project_details";
	}

	@RequestMapping("/list") 
	public String listProjects(Model model) {
		

    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    	String name = auth.getName(); //get logged in username
    	model.addAttribute("username", name);
    	model.addAttribute("projects", projectService.findAll());
		model.addAttribute("title", "Projects");
		return "project-list";
	}
	
	
	@RequestMapping("/refresh") 
	public String projectRefresh(Model model) {
		budgetService.refresh();
		projectService.refresh();
		return "index";
	}
	
	@PostMapping()
	public String saveUser(@ModelAttribute @Valid Project project, BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			
			return "project";
		}
		projectService.save(project);
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    	String name = auth.getName(); //get logged in username
    	model.addAttribute("username", name);
    	model.addAttribute("projects", projectService.findAll());
		model.addAttribute("title", "Projects");		
		model.addAttribute("success", true);
		return "project";
	}
}

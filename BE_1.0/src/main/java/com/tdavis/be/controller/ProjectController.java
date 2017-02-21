package com.tdavis.be.controller;


import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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
import com.tdavis.be.service.BudgetService;
import com.tdavis.be.service.ProjectService;
import com.tdavis.be.service.QuoteService;


@Controller
@RequestMapping("/project")
public class ProjectController {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	//Model Configuration
	private String modelProjectTitle = "Projects";
	private String modelUsername = "username";
	private String modelProjects = "projects";
	private String modelProject = "project";
	private String modelTitle = "title";
	
	//HTML output file
	private String sourceProject = "project";
	
	@Autowired
	private ProjectService projectService;
	
	@Autowired
	private BudgetService budgetService;
	
	@Autowired
	private QuoteService quoteService;
	
	@ModelAttribute("project")
	public Project projectConstruct() {
		return new Project();
	}
	
	@ModelAttribute("budget")
		public Budget budgetConstruct() {
		return new Budget();
	}
	
	//1-Project List - Admin
	@RequestMapping()
	public String showProjects(Model model) {
		Page<Project> page = projectService.getProjectbyYear(1);
				
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    	String name = auth.getName(); //get logged in username
    	model.addAttribute(modelUsername, name);
    	model.addAttribute(modelProjects, page);
		model.addAttribute(modelTitle, modelProjectTitle);
		return sourceProject;
	}
	
	//1-Project List - Admin Details
	@RequestMapping("/{id}")
	public String showProjectDetails(Model model, @PathVariable String id) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    	String name = auth.getName(); //get logged in username
    	model.addAttribute(modelUsername, name);
    	model.addAttribute(modelProject, projectService.findById(Integer.parseInt(id)));
    	model.addAttribute(modelTitle, "Project Details -" + projectService.findById(Integer.parseInt(id)).getName());
		return "project-details";
	}
	
	//2-Project List
	@RequestMapping("/list") 
	public String listProjects(Model model) {
		Page<Project> page = projectService.getProjectbyYear(1);

    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    	String name = auth.getName(); //get logged in username
    	model.addAttribute(modelUsername, name);
    	model.addAttribute(modelProjects, page);
		model.addAttribute(modelTitle, modelProjectTitle);
		return "project-list";
	}
	
	//2-Project List - View Details
	@RequestMapping("/details/{id}")
	public String projectDetails(Model model, @PathVariable String id) {

		double approved = projectService.findById(Integer.parseInt(id)).getBudgetApproved();
		double spent = projectService.findById(Integer.parseInt(id)).getBudgetSpent();
		double balance;
		 
		balance = approved - spent; 

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    	String name = auth.getName(); //get logged in username
    	model.addAttribute(modelUsername, name);
    	model.addAttribute(modelProject, projectService.findById(Integer.parseInt(id)));
    	model.addAttribute("spent",balance);
		model.addAttribute(modelTitle, "Project Details -" + projectService.findById(Integer.parseInt(id)).getName());
		return "project_details";
	}
	
	
	//Update Calculations
	@RequestMapping("/refresh") 
	public String projectRefresh(Model model) {
		budgetService.refresh();
		projectService.refresh();
		quoteService.refresh();
		return "index";
	}
	
	//Delete Project
	@RequestMapping("/delete/{id}")
	public String projectDelete(Model model, @PathVariable int id){
		projectService.delete(id);
		
		Page<Project> page = projectService.getProjectbyYear(1);
		
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    	String name = auth.getName(); //get logged in username
    	model.addAttribute(modelUsername, name);
    	model.addAttribute(modelProjects, page);
		model.addAttribute(modelTitle, modelProjectTitle);
		return sourceProject;
	}
	
	//Add Project
	@PostMapping()
	public String saveUser(@ModelAttribute @Valid Project project, BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			
			return sourceProject;
		}
		logger.info("Saving Project: "+project.getName()+project.getYear());
		projectService.save(project);
		logger.info("Saved Project: "+project.getName()+project.getYear());
		
		Page<Project> page = projectService.getProjectbyYear(1);
		
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    	String name = auth.getName(); //get logged in username
    	model.addAttribute(modelUsername, name);
    	model.addAttribute(modelProjects, page);
		model.addAttribute(modelTitle, modelProjectTitle);		
		model.addAttribute("success", true);
		return sourceProject;
	}
}

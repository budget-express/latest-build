package com.tdavis.be.controller;


import javax.validation.Valid;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
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
import com.tdavis.be.service.ProjectService;



@Controller
@RequestMapping("/project")
public class ProjectController {
	//private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
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
		Page<Project> page = projectService.getProjectByYear(1);
				
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
		
		//Find Project
		Project project = projectService.findById(Integer.parseInt(id));
		
		//Set Model Variables
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    	String name = auth.getName(); //get logged in username
    	model.addAttribute(modelUsername, name);
    	model.addAttribute(modelProject, project);
    	model.addAttribute(modelTitle, "Project Details -" + project.getName());
    	
    	//Return project-details.html
		return "project-details";
	}
	
	//2-Project List
	@RequestMapping("/list") 
	public String listProjects(Model model) {
		Page<Project> page = projectService.getProjectByYear(1);

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
		
		//Call Project Service to Refresh all Calculations
		projectService.refresh();
		
		//Redirect to Index
		return "redirect:/index";
	}
	
	//Delete Project
	@RequestMapping("/delete/{id}")
	public String projectDelete(Model model, @PathVariable int id){

		//Call Project Service to Delete Project
		projectService.delete(id);

		//Redirect to Project
		return "redirect:/project";
	}
	
	//Add Project
	@PostMapping("/save")
	public String saveProject(@ModelAttribute @Valid Project project, BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			
			return sourceProject;
		}
		
		//Call Project Service to Save Project
		projectService.save(project);
		
		//Redirect to Project
		return "redirect:/project";
	}
	
	/*************************************************************************************
	 * 
	 * New Code
	 * 
	 **************************************************************************************/

	
}







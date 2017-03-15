package com.tdavis.be.controller.main;


import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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


import com.tdavis.be.entity.Budget;
import com.tdavis.be.entity.Project;
import com.tdavis.be.entity.User;
import com.tdavis.be.service.HistoryService;
import com.tdavis.be.service.ProjectService;
import com.tdavis.be.service.UserService;



@Controller
@RequestMapping("/main")
public class MainProjectController {

	//Log output to console
	private final Logger temp = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private ProjectService projectService;	
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private HistoryService logger;
	
	//Access project on web page form
	@ModelAttribute("project")
	public Project projectConstruct() {
		return new Project();
	}
	
	//Access budget on web page form
	@ModelAttribute("budget")
	public Budget budgetConstruct() {
		return new Budget();
	}
	
	/********************************************************************************************************
	 *  /Settings/Projects - List(main page), Find/Sort Projects
	 * 	Query Project Objects
	 * 
	 *********************************************************************************************************/
	
	/*
	 * Home >> Settings >> Projects >> All
	 * Listing of Projects
	 */
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping("/projects")
	public String listProjects(Model model) {

		//projects.html
		return "redirect:/main/projects/all";
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping("/project/{id}")
	public String viewProject(Model model, @PathVariable int id) {
		
		//Find Project
		Project project = projectService.findById(id);
		
		//Set Page Navigation
		List<String> navigation = new ArrayList<>();
		
		navigation.add("Main");
		navigation.add("Projects");
		navigation.add(project.getName());
		
		//Find logged in user
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    	User user = userService.findByName(auth.getName());   	
    	
    	//Set Model Attributes
    	model.addAttribute("user", user);
    	model.addAttribute("project", project);
    	model.addAttribute("navigation" , navigation);
    	model.addAttribute("count", projectService.findNumbers(project.getId()));
    	model.addAttribute("percentspent", projectService.getPercentSpent(project));
    	model.addAttribute("percentpending", projectService.getPercentPending(project));
    	model.addAttribute("logs",logger.findAll());
		model.addAttribute("title", project.getName());
		
		return "/main/view-project";
	}
	
	/*
	 * Home >> Settings >> Projects >> All/Active/Planning/Closed
	 * Listing of Projects
	 */
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping("/projects/{sort}")
	public String listProjectsAll(Model model, @PathVariable String sort) {

		//Set Page Navigation
		List<String> navigation = new ArrayList<>();

		navigation.add("Main");
		navigation.add("Projects");
		navigation.add(sort);
		
		switch (sort) {
			case "open":
			case "planning":	
			case "closed":
				model.addAttribute("projects", projectService.getProjectByStatus(sort,1));
				break;
			case "all":
			default:
				model.addAttribute("projects", projectService.getProjectByYear(1));
				break;
		}

		//Find logged in user
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    	User user = userService.findByName(auth.getName());
    	    	
    	//Set Model Attributes
    	model.addAttribute("user", user);
    	model.addAttribute("navtitle", "Projects");
    	model.addAttribute("navigation" , navigation);
		model.addAttribute("title", "Settings>>Projects");
		
		//projects.html
		return "/main/list-projects";
	}
	
	/********************************************************************************************************
	 *  /Settings/Projects - Save, Delete
	 * 	Interact with User/Role Repositories - Access Database
	 * 
	 *********************************************************************************************************/
	
	/*
	 * Home >> Settings >> Projects >> Save
	 * Save Project
	 */
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PostMapping("/project/save")
	public String saveProject(@ModelAttribute @Valid Project project, BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {

			logger.error("project", project.getId(), "Error saving project: "+project.getName(), bindingResult.toString());
			return "redirect:/error";
		}
		//Call Project Service to Save Project
		projectService.save(project);
		
		//Redirect to Projects
		return "redirect:/main/projects";
	}
	
	/*
	 * Home >> Settings >> Projects >> Delete
	 * Delete Project
	 */
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping("/project/delete/{id}")
	@ResponseBody
	public String projectDelete(Model model, @PathVariable int id){

		//Call Project Service to Delete Project
		projectService.delete(id);

		//Redirect to Projects
		return "success";
	}	
}

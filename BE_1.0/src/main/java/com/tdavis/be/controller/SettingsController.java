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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.tdavis.be.entity.Budget;
import com.tdavis.be.entity.Project;
import com.tdavis.be.service.ProjectService;



@Controller
@RequestMapping("/settings")
public class SettingsController {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ProjectService projectService;	
	
	//Access project on web page
	@ModelAttribute("project")
	public Project projectConstruct() {
		return new Project();
	}
	
	/*
	 * Home >> Settings >> Projects >> All
	 * Listing of Projects
	 */
	@RequestMapping("/projects")
	public String listProjects(Model model) {

		Page<Project> page = projectService.getProjectByYear(1);
		
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    	String name = auth.getName(); //get logged in username
    	
    	model.addAttribute("username", name);
    	model.addAttribute("projects", page);
		model.addAttribute("title", "Settings>>Projects");
		return "projects";
	}
	
	/*
	 * Home >> Settings >> Projects >> All
	 * Listing of Projects
	 */
	@RequestMapping("/no/projects/{sort}")
	public String listProjectsAll(RedirectAttributes redir, @PathVariable String sort) {
		logger.info("Sort by: " + sort);
		switch (sort) {
			case "all":
				redir.addAttribute("projects", projectService.findAll());  //getProjectByYear(1));
				break;
			case "active":
			case "planning":
			case "closed":
				redir.addAttribute("projects", projectService.getProjectByStatus(sort,1));
				break;
			default:
				redir.addFlashAttribute("projects", projectService.findAll());
				break;
		}
		logger.info("Exit");
		
    	
		return "redirect:/settings/projects";
	}
	
}

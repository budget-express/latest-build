package com.tdavis.be.controller;


import javax.validation.Valid;

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
import com.tdavis.be.service.ProjectService;


@Controller
@RequestMapping("/project")
public class ProjectController {
	@Autowired
	private ProjectService projectService;
	
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

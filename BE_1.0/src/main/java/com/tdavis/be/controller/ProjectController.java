package com.tdavis.be.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tdavis.be.entity.Project;
import com.tdavis.be.service.ProjectService;


@Controller
public class ProjectController {
	@Autowired
	private ProjectService projectService;
	
	@ModelAttribute("project")
	public Project construct() {
		return new Project();
	}
	
	@RequestMapping("/project/active")
	public String showUsers(Model model) {
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    	String name = auth.getName(); //get logged in username
    	model.addAttribute("username", name);
    	model.addAttribute("projects", projectService.findAll());
		model.addAttribute("title", "Project");
		return "project";
	}
}

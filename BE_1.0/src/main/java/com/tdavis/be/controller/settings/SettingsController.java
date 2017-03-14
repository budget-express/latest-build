package com.tdavis.be.controller.settings;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tdavis.be.entity.User;
import com.tdavis.be.service.HistoryService;
import com.tdavis.be.service.ProjectService;
import com.tdavis.be.service.UserService;



@Controller

public class SettingsController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private ProjectService projectService;
	
	@Autowired
	private HistoryService historyService;
	
	
	/********************************************************************************************************
	 *  /Settings - Menu
	 * 	
	 * 
	 *********************************************************************************************************/
	
	/*
	 * Settings Menu
	 */
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping("/settings")
	public String listSettings(Model model) {
		
		//Set Page Navigation
		List<String> navigation = new ArrayList<>();
		
		navigation.add("Settings");
				
		//Find logged in user
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    	User user = userService.findByName(auth.getName());

    	//Find Numbers
    	int numUsers = userService.getCount();
    	int numProjects = projectService.getCount();
    	int numLogs = historyService.getCount();
    	
    	//Set Model Attributes
    	model.addAttribute("user", user);
	   	model.addAttribute("navigation",navigation);
	   	model.addAttribute("numusers", numUsers);
	   	model.addAttribute("numprojects", numProjects);
	   	model.addAttribute("numlogs", numLogs);
		model.addAttribute("title", "Settings");
		
		return "menu-settings";
	}
}

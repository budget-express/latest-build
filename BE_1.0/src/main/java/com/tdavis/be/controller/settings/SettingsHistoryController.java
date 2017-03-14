package com.tdavis.be.controller.settings;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tdavis.be.entity.History;
import com.tdavis.be.entity.User;
import com.tdavis.be.service.HistoryService;
import com.tdavis.be.service.UserService;


@Controller
@RequestMapping("/settings")
public class SettingsHistoryController {
	
	
	@Autowired
	private HistoryService historyService;
	
	@Autowired
	private UserService userService;
	
	/*
	 * List Logs
	 */
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping("/log")
	public String listUsers(Model model) {
	
		//Set Page Navigation
		List<String> navigation = new ArrayList<>();
		
		navigation.add("Settings");
		navigation.add("Logs");
				
		//Find logged in user
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    	User user = userService.findByName(auth.getName());
    	    	
    	//Set Model Attributes
    	model.addAttribute("user", user);
	   	model.addAttribute("navigation",navigation);
	   	model.addAttribute("logs", historyService.getHistoryByDate(1));
		model.addAttribute("title", "Settings>>Logs");
		
		return "/settings/log";
	}
	
	
	/*
	 * Find History by User ID
	 */
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping("/history/user/{id}")
	@ResponseBody
	public Iterable<History> findUserHistory(Model model, @PathVariable int id) {
		//Return History page
		return historyService.getHistoryByDate(1);
	}

}

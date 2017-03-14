package com.tdavis.be.controller.settings;


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

import com.tdavis.be.entity.User;
import com.tdavis.be.service.HistoryService;
import com.tdavis.be.service.UserService;



@Controller
@RequestMapping("/settings")
public class SettingsUserController {
	
	//Log output to console
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	//Constants
	private String redirectUsers = "redirect:/settings/users";

	@Autowired
	private UserService userService;
	
	@Autowired
	private HistoryService historyService;
	
	//Access user on web page
	@ModelAttribute("user")
	public User construct() {
		return new User();
	}
	
	//Access Roles on web page
	@ModelAttribute("userRoles")
	public List<String> constructRole() {
		return new ArrayList<>();
	}

	/********************************************************************************************************
	 *  /Settings/Users - Main Page
	 * 	Query User/Role Objects
	 * 
	 *********************************************************************************************************/
	
	/*
	 * List Users/Roles
	 */
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping("/users")
	public String listUsers(Model model) {
	
		//Set Page Navigation
		List<String> navigation = new ArrayList<>();
		
		navigation.add("Settings");
		navigation.add("Users");
				
		//Find logged in user
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    	User user = userService.findByName(auth.getName());
    	    	
    	//Set Model Attributes
    	model.addAttribute("user", user);
	   	model.addAttribute("navigation",navigation);
	   	model.addAttribute("navtitle", "Users");
	   	model.addAttribute("users", userService.findAll());
	   	model.addAttribute("roles", userService.findAllRoles());
	   	model.addAttribute("logs", historyService.getHistoryByDate(1));
		model.addAttribute("title", "Settings>>Users");
		
		return "/settings/users";
	}
	
	/********************************************************************************************************
	 *  /Settings/Users - Save, Enable, Delete
	 * 	Interact with User/Role Repositories - Access Database
	 * 
	 *********************************************************************************************************/
	
	/*
	 * Save User
	 */
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PostMapping("/user/save")
	public String saveUser2(@ModelAttribute @Valid User user, BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			logger.error(bindingResult+"");
			return "/error";
		}
		
		//Call User Services to Save User
		userService.save(user);

		//Set Success for Deletion
		model.addAttribute("success", true);
		
		//Redirect to User Details
		return redirectUsers;
	}
	
	/*
	 * Enable/Disable User
	 */
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping("/user/enable/{id}")
	@ResponseBody
	public String enableUser(Model model, @PathVariable int id) {
		
		//Toggle User Enabled
		User user = userService.findById(id);
		if (user.isEnabled()) {
			user.setEnabled(false);
		} else {
			user.setEnabled(true);
		}
		
		//Call User Services to Save User
		userService.save(user);
		
		//Set Success for Deletion
		model.addAttribute("success", true);
		
		//Redirect to User Details
		return "success";
	}
	
	/*
	 * Delete User
	 */
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping("/user/delete/{id}")
	@ResponseBody
	public String deleteUser(Model model, @PathVariable int id) {
		
		//Call User Services to Delete User
		userService.delete(id);
		
		//Set Success for Deletion
		model.addAttribute("success", true);
		
		//Redirect to User Details
		return "success";
	}
}

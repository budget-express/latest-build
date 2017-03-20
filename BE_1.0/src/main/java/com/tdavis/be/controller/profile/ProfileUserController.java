package com.tdavis.be.controller.profile;


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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.tdavis.be.entity.User;
import com.tdavis.be.service.HistoryService;
import com.tdavis.be.service.UserService;



@Controller
@RequestMapping("/profile")
public class ProfileUserController {
	//Log output to console
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private UserService userService;
	
	//Access user on web page
	@ModelAttribute("user")
	public User construct() {
		return new User();
	}
	
	/*
	 * Save User
	 */
	@PreAuthorize("hasRole('ROLE_USER')")
	@RequestMapping("/user/{id}")
	public String setUserPassword(@PathVariable int id, Model model) {
		User user = userService.findById(id);
		
		//Set Page Navigation
		List<String> navigation = new ArrayList<>();
		
		navigation.add("Settings");
		navigation.add("Users");
		
    	//Set Model Attributes
    	model.addAttribute("user", user);
	   	model.addAttribute("navigation",navigation);
	   	model.addAttribute("navtitle", "Users");
		model.addAttribute("title", "Profile>>"+user.getName());
		
		return "profile/view-profile";
	}
}

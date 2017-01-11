package com.tdavis.be.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.validation.BindingResult;

import com.tdavis.be.entity.User;
import com.tdavis.be.service.UserService;

@Controller
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@ModelAttribute("user")
	public User construct() {
		return new User();
	}
	
	
	@RequestMapping("/admin")
	public String showUsers(Model model) {
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    	String name = auth.getName(); //get logged in username
    	model.addAttribute("username", name);
    	model.addAttribute("users", userService.findAll());
		model.addAttribute("title", "Admin");
		return "admin";
	}
	
	@PostMapping("/admin")
	public String saveUser(@ModelAttribute @Valid User user, BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {
			
			return "admin";
		}
		userService.save(user);
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    	String name = auth.getName(); //get logged in username
    	model.addAttribute("username", name);
    	model.addAttribute("users", userService.findAll());
		model.addAttribute("title", "Admin");		
		model.addAttribute("success", true);
		return "admin";
	}
}
package com.tdavis.be.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tdavis.be.entity.User;
import com.tdavis.be.service.BlogService;
import com.tdavis.be.service.UserService;

@Controller
public class IndexController {
 
	@Autowired
	private UserService userService;
	
	@Autowired
	private BlogService blogService;
	
    @RequestMapping("/")
    String index(Model model){
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    	User user = userService.findByName(auth.getName());
    	
    	model.addAttribute("title", "Budget Tracker");
    	model.addAttribute("user", user);
    	model.addAttribute("blogs", blogService.getBlogByDateCreated(1));
    	
        return "index";
    }
    
    @RequestMapping("/old/index")
    String index2(Model model){
    	
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    	User user = userService.findByName(auth.getName());
    	
    	model.addAttribute("title", "Budget Tracker");
    	model.addAttribute("user", user);
    	
    	return "old/index";
    }
}
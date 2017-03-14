package com.tdavis.be.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tdavis.be.entity.User;
import com.tdavis.be.service.UserService;

@Controller
public class IndexController {
 
	@Autowired
	private UserService userService;
	
    @RequestMapping("/")
    String index(Model model){
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    	User user = userService.findByName(auth.getName());
    	
    	model.addAttribute("title", "Budget Tracker");
    	model.addAttribute("user", user);
        return "index2";
    }
    
    @RequestMapping("/index2")
    String index2(Model model){
    	
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    	User user = userService.findByName(auth.getName());
    	
    	model.addAttribute("title", "Budget Tracker");
    	model.addAttribute("user", user);
    	
    	//model.addAttribute("username", user.getName());
    	//model.addAttribute("name", user.getFname() + " " + user.getLname());
    	//model.addAttribute("usertitle", userService.findByName(name).getTitle());

    	return "index2";
    }
}
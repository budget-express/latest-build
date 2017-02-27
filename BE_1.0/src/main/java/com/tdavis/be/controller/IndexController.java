package com.tdavis.be.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.tdavis.be.service.UserService;

@Controller
public class IndexController {
 
	@Autowired
	private UserService userService;
	
    @RequestMapping("/")
    String index(Model model){
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    	String name = auth.getName(); //get logged in username
    	model.addAttribute("title", "Budget Express");
    	model.addAttribute("username", name);
        return "index";
    }
    
    @RequestMapping("/index2")
    String index2(Model model){
    	
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    	String name = auth.getName(); //get logged in username
    	model.addAttribute("title", "Budget Tracker");
    	model.addAttribute("username", name);
    	model.addAttribute("usertitle", userService.findByName(name).getTitle());

    	return "index2";
    }
}
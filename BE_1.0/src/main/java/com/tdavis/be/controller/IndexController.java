package com.tdavis.be.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class IndexController {
 
    @RequestMapping("/")
    String index(Model model){
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    	String name = auth.getName(); //get logged in username
    	model.addAttribute("title", "Budget Express");
    	model.addAttribute("username", name);
        return "index";
    }
}
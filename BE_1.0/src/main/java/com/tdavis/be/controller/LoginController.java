package com.tdavis.be.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class LoginController {

	@RequestMapping(value="/login")
	public String printUser(Model model) {
		model.addAttribute("title", "Budget Express - Login");
		return "login2";
	}
}
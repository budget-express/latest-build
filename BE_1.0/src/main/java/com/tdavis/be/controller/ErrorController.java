package com.tdavis.be.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/error/block")
public class ErrorController {

	@RequestMapping("/")
	public String error() {
		return "error";
	}
}

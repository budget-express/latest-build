package com.tdavis.be.controller.settings;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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

import com.tdavis.be.entity.Blog;
import com.tdavis.be.entity.User;
import com.tdavis.be.service.BlogService;
import com.tdavis.be.service.HistoryService;
import com.tdavis.be.service.UserService;

@Controller
@RequestMapping("/settings")
public class SettingsBlogController {

	@Autowired
	private BlogService blogService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private HistoryService logger;
	
	//Access blog on web page form
	@ModelAttribute("blog")
	public Blog blogConstruct() {
		return new Blog();
	}
	
	/********************************************************************************************************
	 *  /Settings/Projects - List(main page), Find/Sort Projects
	 * 	Query Project Objects
	 * 
	 *********************************************************************************************************/
	
	/*
	 * Home >> Settings >> Projects >> All
	 * Listing of Projects
	 */
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping("/blogs")
	public String listProjects(Model model) {
		
		//Set Page Navigation
		List<String> navigation = new ArrayList<>();

		navigation.add("Settings");
		navigation.add("Blogs");
		
		//Find Projects sort by Year (page)
		Page<Blog> page = blogService.getBlogByDateCreated(1);
		
		//Find logged in user
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
    	User user = userService.findByName(auth.getName());
    	    	
    	//Set Model Attributes
    	model.addAttribute("user", user);
    	model.addAttribute("blogs", page);
    	model.addAttribute("navtitle", "Blogs");
    	model.addAttribute("navigation" , navigation);
		model.addAttribute("title", "Settings>>Blogs");
		
		//projects.html
		return "/settings/list-blogs";
	}
	
	/********************************************************************************************************
	 *  /Settings/Blogs - Save, Delete
	 * 	Interact with Repositories - Access Database
	 * 
	 *********************************************************************************************************/
	
	/*
	 * Home >> Settings >> Blog >> Save
	 * Save Blog
	 */
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PostMapping("/blog/save")
	public String saveProject(@ModelAttribute @Valid Blog blog, BindingResult bindingResult, Model model) {
		if (bindingResult.hasErrors()) {

			logger.error("system", blog.getId(), "Error saving project: "+blog.getName(), bindingResult.toString());
			return "redirect:/error";
		}
		
		//Call Project Service to Save Project
		blogService.save(blog);
		
		//Redirect to Projects
		return "redirect:/settings/blogs";
	}
	
	/*
	 * Home >> Settings >> Projects >> Delete
	 * Delete Project
	 */
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping("/blog/delete/{id}")
	@ResponseBody
	public String projectDelete(Model model, @PathVariable int id){

		//Call Project Service to Delete Project
		blogService.delete(id);

		//Redirect to Projects
		return "success";
	}		
}

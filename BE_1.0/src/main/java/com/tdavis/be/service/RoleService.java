package com.tdavis.be.service;


import javax.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import org.springframework.security.access.prepost.PreAuthorize;

import com.tdavis.be.entity.Budget;
import com.tdavis.be.entity.FileUpload;
import com.tdavis.be.entity.Project;
import com.tdavis.be.entity.Quote;
import com.tdavis.be.entity.Role;
import com.tdavis.be.entity.User;
import com.tdavis.be.repository.ProjectRepository;
import com.tdavis.be.repository.RoleRepository;


@Service
@Transactional
public class RoleService {

	@Autowired
	private RoleRepository roleRepository;
	
	public List<Role> setRoles (User user) {
		
		User temp = user;
		
		//Set Roles
		List<Role> roles = new ArrayList<Role>();
		if (temp.isEnabled()) {
			roles.add(roleRepository.findByName("ROLE_USER"));
		}
		if (temp.isAdmin()) {
			roles.add(roleRepository.findByName("ROLE_ADMIN"));
		}
		
		return roles;
	}
	
}

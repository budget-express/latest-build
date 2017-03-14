package com.tdavis.be.service;

import javax.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

import com.tdavis.be.entity.Role;
import com.tdavis.be.entity.User;
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

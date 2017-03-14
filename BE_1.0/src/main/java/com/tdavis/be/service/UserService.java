package com.tdavis.be.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.tdavis.be.entity.Role;
import com.tdavis.be.entity.User;
import com.tdavis.be.repository.RoleRepository;
import com.tdavis.be.repository.UserRepository;

@Service
@Transactional
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private RoleService roleService;
	
	@Autowired
	private HistoryService logger;
	
	public Iterable<User> findAll () {
		
		return userRepository.findAll();
		
	}
	
	public User findById(int id) {
		return userRepository.findById(id);
	}
	
	public User findByName(String name) {
		return userRepository.findByName(name);
	}
	
	public Iterable<Role> findAllRoles () {
		return roleRepository.findAll();
	}
	
	public void save(User user) {


		
    	String message;
		
		if (user.getId() != null) {
			//Preserve Password
			user.setPassword(userRepository.findById(user.getId()).getPassword());
			
			//Set Roles
			user.setRoles(roleService.setRoles(user));
			
			message = "Updated User " + user.getName();

		} else {
			//Encrypt Password
			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
			user.setPassword(encoder.encode(user.getPassword()));

			//Set Roles
			user.setRoles(roleService.setRoles(user));
			
			message = "Created User " + user.getName();
		}

		userRepository.save(user);
		logger.info("system", 0 , message);

	}
	
	public void edit(User user) {
	
		User temp = userRepository.findOne(user.getId());
		temp.setId(user.getId());
		temp.setName(user.getName());
		temp.setFname(user.getFname());
		temp.setLname(user.getLname());
		temp.setEmail(user.getEmail());
		temp.setTitle(user.getTitle());
		
		List<Role> roles = new ArrayList<Role>();
		if (user.isEnabled()){
			roles.add(roleRepository.findByName("ROLE_USER"));
		}
		if (user.isAdmin()) {
			roles.add(roleRepository.findByName("ROLE_ADMIN"));
		}
		temp.setRoles(roles);
		
		userRepository.save(temp);
	}
	
	public void delete(Integer id) {
		User temp = userRepository.findOne(id);
		userRepository.delete(temp);
		logger.info("system", 0 , "Deleted User " + temp.getName());
	}

	public void updateRoles(User user, ArrayList<String> userRoles) {

		List<Role> roles = new ArrayList<Role>();
		for (String temp : userRoles) {
			roles.add(roleRepository.findByName(temp));
		}
		user.setRoles(roles);
		
		userRepository.save(user);
		
	}
	
	@SuppressWarnings("unused")
	public Integer getCount() {
		int size = 0;
		for(User user : userRepository.findAll()) {
		   size++;
		}
		
		return size;
	}
	

}

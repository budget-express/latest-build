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
	private HistoryService logger;


	/**************************************************************************************************************************
	 * 
	 * User Interactions with Service Layer
	 * 
	 * @param user
	 */	
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

	/**************************************************************************************************************************
	 * 
	 * User Interactions with User Repository
	 * Save/Edit, Delete, Enable/Disable, Change Password
	 * 
	 * @param user
	 */

	/*
	 * Save User 
	 */	
	public void save(User user) {

    	String message;
		
		if (user.getId() != null) {
			//Preserve Password
			user.setPassword(userRepository.getOne(user.getId()).getPassword());
			
			//Set Roles
			user.setRoles(userRepository.getOne(user.getId()).getRoles());
			
			message = "Updated User " + user.getName();

		} else {
						
			//Encrypt Password
			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
			user.setPassword(encoder.encode(user.getPassword()));
			
			List<Role> roles = new ArrayList<Role>();
			roles.add(roleRepository.findByName("ROLE_USER"));
			if (user.isAdmin()) {
				roles.add(roleRepository.findByName("ROLE_ADMIN"));
			} 
			user.setRoles(roles);
			
			message = "Created User " + user.getName();
		}
		
		//Save User to Repository
		userRepository.save(user);
		logger.system( message);

	}
	
	/*
	 * Change User Password
	 */
	public void changePassword(User user) {
		
		//Temp User
		User temp = userRepository.getOne(user.getId());
		
		//Encrypt and Set Password
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		temp.setPassword(encoder.encode(user.getPassword()));
		
		//Save User to Repository
		userRepository.save(temp);
		logger.info("system", 0, "Changed Password for "+ temp.getFname() + " " + temp.getLname() + " (" + temp.getName() + ")");
	}

	/*
	 * Delete User
	 */
	public void delete(Integer id) {
		//Temp User
		User temp = userRepository.findOne(id);
		
		//Delete User from Repository
		userRepository.delete(temp);
		logger.info("system", 0 , "Deleted User " + temp.getName());
	}

	
	/*
	 * ?????Update Roles?????
	 */
	public void updateRoles(User user, ArrayList<String> userRoles) {

		List<Role> roles = new ArrayList<Role>();
		for (String temp : userRoles) {
			roles.add(roleRepository.findByName(temp));
		}
		user.setRoles(roles);
		
		userRepository.save(user);
	}
	
	/*
	 * Get # of Users
	 */
	@SuppressWarnings("unused")
	public Integer getCount() {
		int size = 0;
		for(User user : userRepository.findAll()) {
		   size++;
		}
		return size;
	}
	
}

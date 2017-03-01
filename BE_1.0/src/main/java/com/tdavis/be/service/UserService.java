package com.tdavis.be.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

	//Log output to console
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
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
		
		if (user.getId() != null) {
			logger.info("User Password: "+ user.getPassword());
			user.setPassword(userRepository.findById(user.getId()).getPassword());
		} else {
			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
			user.setPassword(encoder.encode(user.getPassword()));
		}
		
		List<Role> roles = new ArrayList<Role>();
		if (user.isEnabled()) {
			roles.add(roleRepository.findByName("ROLE_USER"));
		}
		user.setRoles(roles);
		
		userRepository.save(user);

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
	}

	public void updateRoles(User user, ArrayList<String> userRoles) {

		List<Role> roles = new ArrayList<Role>();
		for (String temp : userRoles) {
			roles.add(roleRepository.findByName(temp));
		}
		user.setRoles(roles);
		
		userRepository.save(user);
		
	}
}

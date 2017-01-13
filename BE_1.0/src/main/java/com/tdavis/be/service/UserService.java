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
	
	public Iterable<User> findAll () {
		
		return userRepository.findAll();
		
	}
	
	public void save(User user) {
		user.setEnabled(true);
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		user.setPassword(encoder.encode(user.getPassword()));

		List<Role> roles = new ArrayList<Role>();
		roles.add(roleRepository.findByName("ROLE_USER"));
		if (user.isAdmin()) {
			roles.add(roleRepository.findByName("ROLE_ADMIN"));
		}
		user.setRoles(roles);
		
		userRepository.save(user);

	}
	
	public void edit(User user) {
		User temp = userRepository.findOne(user.getId());
		temp.setName(user.getName());
		temp.setFname(user.getFname());
		temp.setLname(user.getLname());
		temp.setEmail(user.getEmail());
		temp.setTitle(user.getTitle());
		
		userRepository.save(temp);
		
	}
	
}

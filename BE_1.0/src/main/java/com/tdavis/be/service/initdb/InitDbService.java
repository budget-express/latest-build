package com.tdavis.be.service.initdb;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.tdavis.be.entity.Role;
import com.tdavis.be.entity.User;
import com.tdavis.be.repository.RoleRepository;
import com.tdavis.be.repository.UserRepository;



@Transactional
@Service
public class InitDbService {

	
	@Autowired
	private RoleRepository roleRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@PostConstruct
	public void init() throws IOException {
		
		if (userRepository != null && userRepository.count() == 0) {
		
			Role roleUser = new Role();
			roleUser.setName("ROLE_USER");
			roleRepository.save(roleUser);
			
			Role roleAdmin = new Role();
			roleAdmin.setName("ROLE_ADMIN");
			roleRepository.save(roleAdmin);
			
			User userAdmin = new User();
			userAdmin.setEnabled(true);
			userAdmin.setAdmin(true);
			userAdmin.setName("admin");
			userAdmin.setFname("admin");
			userAdmin.setLname("admin");
			BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
			userAdmin.setPassword(encoder.encode("admin"));
			List<Role> roles = new ArrayList<Role>();
			roles.add(roleAdmin);
			roles.add(roleUser);
			userAdmin.setRoles(roles);
			userRepository.save(userAdmin);
			
			User userUser = new User();
			userUser.setEnabled(true);
			userUser.setName("user");
			userUser.setFname("user");
			userUser.setLname("user");
			userUser.setPassword(encoder.encode("user"));
			List<Role> uroles = new ArrayList<Role>();
			uroles.add(roleUser);
			userUser.setRoles(uroles);
			userRepository.save(userUser);

		}
		
		
	}
	
}

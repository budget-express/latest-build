package com.tdavis.be.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.tdavis.be.entity.User;


public interface UserRepository extends JpaRepository<User, Integer>{
	User findByFname (String fname);
	User findByLname (String lname);
}
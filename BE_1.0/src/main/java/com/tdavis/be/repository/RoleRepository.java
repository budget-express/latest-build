package com.tdavis.be.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.tdavis.be.entity.Role;

public interface RoleRepository extends JpaRepository<Role, Integer>{ 
	Role findByName(String name);
}
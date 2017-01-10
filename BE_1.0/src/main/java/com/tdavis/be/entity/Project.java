package com.tdavis.be.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;

@Entity
public class Project {

	@Id
	@GeneratedValue
	private Integer id;

	private String name;
	
	private String description;
	
	private boolean enabled;
	
	private String Year;
	
	@OneToMany(mappedBy = "project", cascade = CascadeType.REMOVE)
	private List<Budget> budgets;
	
}

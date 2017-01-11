package com.tdavis.be.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class History {

	@Id
	@GeneratedValue
	private Integer id;

	private String name;
	
	
}

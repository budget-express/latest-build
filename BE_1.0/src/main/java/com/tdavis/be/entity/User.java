package com.tdavis.be.entity;


import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.Email;


@Entity
@Table(name = "app_user")
public class User {

	@Id
	@GeneratedValue
	private Integer id;

	@NotNull
	@Size(min = 3, message = "Name must be at least 3 characters!")
	@Column(unique = true)
	private String fname;
	
	@NotNull
	@Size(min = 3, message = "Name must be at least 3 characters!")
	@Column(unique = true)
	private String lname;

	@NotNull
	private String name;
	
	@Size(min = 1, message = "Invalid email address!")
	@Email(message = "Invalid email address!")
	private String email;
	
	private String title;
	
	@Size(min = 5, message = "Password must be at least 5 characters!")
	private String password;

	private boolean enabled;
	
	private boolean admin;

	@ManyToMany
	@JoinTable(name="app_user_role")
	private List<Role> roles;
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
	private List<History> historys;
	
	
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<History> getHistorys() {
		return historys;
	}

	public void setHistorys(List<History> historys) {
		this.historys = historys;
	}

	public String getFname() {
		return fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public String getLname() {
		return lname;
	}

	public void setLname(String lname) {
		this.lname = lname;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public boolean isAdmin() {
		return admin;
	}

	public void setAdmin(boolean admin) {
		this.admin = admin;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}


	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}

package com.tdavis.be.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class History {

	@Id
	@GeneratedValue
	private Integer id;

	//info-error-warning-debug
	private String type;
	
	//project-budget-quote-file
	private String object;
	
	private Integer objectId;
	
	private String user;
	
	private Integer userId;

	@Column(length = 3000)
	private String message;
	
	private String payload;
	
	private Date date;
	
	public History() {
		this.date = new Date();
	}
	
	public History(String type, String object, Integer objectId, String user, Integer userId,
			String message, String payload) {
		super();
		this.type = type;
		this.object = object;
		this.objectId = objectId;
		this.user = user;
		this.userId = userId;
		this.message = message;
		this.payload = payload;
		this.date = new Date();
	}
	
	public History(String type, String object, Integer objectId, String user, Integer userId,
			String message) {
		super();
		this.type = type;
		this.object = object;
		this.objectId = objectId;
		this.user = user;
		this.userId = userId;
		this.message = message;
		this.date = new Date();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getObject() {
		return object;
	}

	public void setObject(String object) {
		this.object = object;
	}

	public Integer getObjectId() {
		return objectId;
	}

	public void setObjectId(Integer objectId) {
		this.objectId = objectId;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getPayload() {
		return payload;
	}

	public void setPayload(String payload) {
		this.payload = payload;
	}

	public Date getDate() {
		return date;
	}
	
}

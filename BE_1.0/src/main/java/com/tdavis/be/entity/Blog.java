package com.tdavis.be.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Blog {

	@Id
	@GeneratedValue
	private Integer id;
	
	private String name;
	private String message;
	
	boolean enabled;
	
    //Logging
    private String editedBy;
    private String createdBy;
    private String enabledBy;
    private String disabledBy;
	
	//Timestamp
	private Date dateCreated;
	private Date dateEdited;
	private Date dateEnabled;
	private Date dateDisabled;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getEditedBy() {
		return editedBy;
	}
	public void setEditedBy(String editedBy) {
		this.editedBy = editedBy;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getEnabledBy() {
		return enabledBy;
	}
	public void setEnabledBy(String enabledBy) {
		this.enabledBy = enabledBy;
	}
	public String getDisabledBy() {
		return disabledBy;
	}
	public void setDisabledBy(String disabledBy) {
		this.disabledBy = disabledBy;
	}
	public Date getDateCreated() {
		return dateCreated;
	}
	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}
	public Date getDateEdited() {
		return dateEdited;
	}
	public void setDateEdited(Date dateEdited) {
		this.dateEdited = dateEdited;
	}
	public Date getDateEnabled() {
		return dateEnabled;
	}
	public void setDateEnabled(Date dateEnabled) {
		this.dateEnabled = dateEnabled;
	}
	public Date getDateDisabled() {
		return dateDisabled;
	}
	public void setDateDisabled(Date dateDisabled) {
		this.dateDisabled = dateDisabled;
	}
	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean isEnabled) {
		this.enabled = isEnabled;
	}
	
	
}

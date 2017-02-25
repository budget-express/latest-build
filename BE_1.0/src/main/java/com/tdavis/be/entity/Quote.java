package com.tdavis.be.entity;


import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;


@Entity
public class Quote {

	@Id
	@GeneratedValue
	private Integer id;

	private String name;
	private double capex;
	private double opex;
	private String justification;
	private String vendor;
	private String po;
	
	//Planning/Open/Closed
	private String status;
	private String vendorContact;
	private String vendorEmail;
	
	//Timestamps
	private String dateCreated;
	private String dateEdited;
	private String dateEnabled; 
    private String dateDisabled;
    
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "budget_id")
	private Budget budget;
	
	@OneToMany(cascade = CascadeType.REMOVE)
	@JoinColumn(name = "quote_id")
	private List<FileUpload> fileUploads;
	
	

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

	public double getCapex() {
		return capex;
	}

	public void setCapex(double capex) {
		this.capex = capex;
	}

	public double getOpex() {
		return opex;
	}

	public void setOpex(double opex) {
		this.opex = opex;
	}

	public String getJustification() {
		return justification;
	}

	public void setJustification(String justification) {
		this.justification = justification;
	}

	public String getVendor() {
		return vendor;
	}

	public void setVendor(String vendor) {
		this.vendor = vendor;
	}

	public String getPo() {
		return po;
	}

	public void setPo(String po) {
		this.po = po;
	}

	public Budget getBudget() {
		return budget;
	}

	public void setBudget(Budget budget) {
		this.budget = budget;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(String dateCreated) {
		this.dateCreated = dateCreated;
	}

	public String getDateEdited() {
		return dateEdited;
	}

	public void setDateEdited(String dateEdited) {
		this.dateEdited = dateEdited;
	}

	public List<FileUpload> getFileUploads() {
		return fileUploads;
	}

	public void setFileUploads(List<FileUpload> fileUploads) {
		this.fileUploads = fileUploads;
	}

	public String getVendorContact() {
		return vendorContact;
	}

	public void setVendorContact(String vendorContact) {
		this.vendorContact = vendorContact;
	}

	public String getVendorEmail() {
		return vendorEmail;
	}

	public void setVendorEmail(String vendorEmail) {
		this.vendorEmail = vendorEmail;
	}

	public String getDateEnabled() {
		return dateEnabled;
	}

	public void setDateEnabled(String dateEnabled) {
		this.dateEnabled = dateEnabled;
	}

	public String getDateDisabled() {
		return dateDisabled;
	}

	public void setDateDisabled(String dateDisabled) {
		this.dateDisabled = dateDisabled;
	}
	
	
	
}

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
	
	private String status;
	
	private String created;
	
	private String edited;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "budget_id")
	private Budget budget;
	
	@OneToMany(cascade = CascadeType.REMOVE)
	@JoinColumn(name="quote_id")
	private List<QuoteFile> files;
	
	

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

	public String getCreated() {
		return created;
	}

	public void setCreated(String created) {
		this.created = created;
	}

	public String getEdited() {
		return edited;
	}

	public void setEdited(String edited) {
		this.edited = edited;
	}

	public List<QuoteFile> getFiles() {
		return files;
	}

	public void setFiles(List<QuoteFile> files) {
		this.files = files;
	}
	
	
	
}

package com.tdavis.be.entity;


import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Quote {

	@Id
	@GeneratedValue
	private Integer id;

	private String name;
	
	private double capex;
	
	private double opex;
	
	//private List<Byte []> quote_files;
	
	private String justification;
	
	private String vendor;
	
	private String po;
	
	private String status;
	
	private String created;
	
	private String edited;
	
	//private List<Byte []> po_files;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "budget_id")
	private Budget budget;
	
	

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

	//public List<Byte[]> getQuote_files() {
	//	return quote_files;
	//}

	//public void setQuote_files(List<Byte[]> quote_files) {
	//	this.quote_files = quote_files;
	//}

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

	//public List<Byte[]> getPo_files() {
	//	return po_files;
	//}

	//public void setPo_files(List<Byte[]> po_files) {
	//	this.po_files = po_files;
	//}

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
	
	
}

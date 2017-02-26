package com.tdavis.be.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

@Entity
public class Project {

	@Id
	@GeneratedValue
	private Integer id;

	//Profile Variables
	private String name;
	private String description;
	private boolean enabled;
	private String year;
	
	//Budget Variables
	private double budgetRequested;
	private double budgetApproved;
	private double budgetSpent;
	private double budgetPending;	
	private double budgetStaged;
	
	//Date Variables
	private String dateCreated;
	private String dateEdited;
	private String dateEnabled;
	private String dateDisabled;
	
	//Project/Infrastructure
	private String type;
	
	//Planning/Open/Closed
	private String status;
	
	@OneToMany(cascade = CascadeType.REMOVE)
	@JoinColumn(name="project_id")
	private List<Budget> budgets;
	
	@OneToMany(mappedBy = "project", cascade = CascadeType.REMOVE)
	private List<History> historys;

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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public List<Budget> getBudgets() {
		return budgets;
	}

	public void setBudgets(List<Budget> budgets) {
		this.budgets = budgets;
	}

	

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<History> getHistorys() {
		return historys;
	}

	public void setHistorys(List<History> historys) {
		this.historys = historys;
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

	public double getBudgetRequested() {
		return budgetRequested;
	}

	public void setBudgetRequested(double budgetRequested) {
		this.budgetRequested = budgetRequested;
	}

	public double getBudgetApproved() {
		return budgetApproved;
	}

	public void setBudgetApproved(double budgetApproved) {
		this.budgetApproved = budgetApproved;
	}

	public double getBudgetSpent() {
		return budgetSpent;
	}

	public void setBudgetSpent(double budgetSpent) {
		this.budgetSpent = budgetSpent;
	}

	public double getBudgetPending() {
		return budgetPending;
	}

	public void setBudgetPending(double budgetPending) {
		this.budgetPending = budgetPending;
	}

	public double getBudgetStaged() {
		return budgetStaged;
	}

	public void setBudgetStaged(double budgetStaged) {
		this.budgetStaged = budgetStaged;
	}
	
	
	
}

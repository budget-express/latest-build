package com.tdavis.be.entity;

import java.util.Date;
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

	//Project/Infrastructure/Special
	private String type;
	
	//Planning/Open/Closed
	private String status;
	
	@OneToMany(cascade = CascadeType.REMOVE)
	@JoinColumn(name="project_id")
	private List<Budget> budgets;

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

	
}

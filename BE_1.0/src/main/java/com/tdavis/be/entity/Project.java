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

	private String name;
	
	private String description;
	
	private boolean enabled;
	
	private String Year;
	
	private double requested_budget;
	
	private double approved_budget;
	
	private double spent_budget;
	
	private double pending_budget;
	
	private double staged_budget;
	
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
		return Year;
	}

	public void setYear(String year) {
		Year = year;
	}

	public List<Budget> getBudgets() {
		return budgets;
	}

	public void setBudgets(List<Budget> budgets) {
		this.budgets = budgets;
	}

	public double getAproved_budget() {
		return approved_budget;
	}

	public void setAproved_budget(double aproved_budget) {
		this.approved_budget = aproved_budget;
	}

	public double getSpent_budget() {
		return spent_budget;
	}

	public void setSpent_budget(double spent_budget) {
		this.spent_budget = spent_budget;
	}

	public double getRequested_budget() {
		return requested_budget;
	}

	public void setRequested_budget(double requested_budget) {
		this.requested_budget = requested_budget;
	}

	public double getPending_budget() {
		return pending_budget;
	}

	public void setPending_budget(double pending_budget) {
		this.pending_budget = pending_budget;
	}

	public double getStaged_budget() {
		return staged_budget;
	}

	public void setStaged_budget(double staged_budget) {
		this.staged_budget = staged_budget;
	}

	public double getApproved_budget() {
		return approved_budget;
	}

	public void setApproved_budget(double approved_budget) {
		this.approved_budget = approved_budget;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	
}

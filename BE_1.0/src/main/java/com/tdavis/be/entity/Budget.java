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
public class Budget {

	@Id
	@GeneratedValue
	private Integer id;

	//Profile 
	private String name;
    private String descriptionShort;
    private String descriptionLong;
    private String year;
    private String budgetCode;
    
    //Details
    private String status;
    private String category;
    private String region;
    private String criticality;
    private int ranking;
    
    //Breakout
    private double q1;
    private boolean enabledQ1;
    private double q2;
    private boolean enabledQ2;
    private double q3;
    private boolean enabledQ3;
    private double q4;
    private boolean enabledQ4;
    
    //Variables
    private double quoteSpent;
    private double quotePending;
    private double quoteStaged;
    private double budgetRemaining;
    private double budgetRequested;
    private double budgetApproved;
    
    //Timestamp
    private String dateCreated;
    private String dateEdited;
    private String dateEnabled; 
    private String dateDisabled;
    private String dateQ1Enabled;
    private String dateQ2Enabled;
    private String dateQ3Enabled;
    private String dateQ4Enabled;
    
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "project_id")
	private Project project;
	
	@OneToMany(cascade = CascadeType.REMOVE)
	@JoinColumn(name="budget_id")
	private List<Quote> quotes;

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

	public String getDescriptionShort() {
		return descriptionShort;
	}

	public void setDescriptionShort(String descriptionShort) {
		this.descriptionShort = descriptionShort;
	}

	public String getDescriptionLong() {
		return descriptionLong;
	}

	public void setDescriptionLong(String descriptionLong) {
		this.descriptionLong = descriptionLong;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
	}

	public String getBudgetCode() {
		return budgetCode;
	}

	public void setBudgetCode(String budgetCode) {
		this.budgetCode = budgetCode;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getCriticality() {
		return criticality;
	}

	public void setCriticality(String criticality) {
		this.criticality = criticality;
	}

	public int getRanking() {
		return ranking;
	}

	public void setRanking(int ranking) {
		this.ranking = ranking;
	}

	public double getQ1() {
		return q1;
	}

	public void setQ1(double q1) {
		this.q1 = q1;
	}

	public boolean isEnabledQ1() {
		return enabledQ1;
	}

	public void setEnabledQ1(boolean enabledQ1) {
		this.enabledQ1 = enabledQ1;
	}

	public double getQ2() {
		return q2;
	}

	public void setQ2(double q2) {
		this.q2 = q2;
	}

	public boolean isEnabledQ2() {
		return enabledQ2;
	}

	public void setEnabledQ2(boolean enabledQ2) {
		this.enabledQ2 = enabledQ2;
	}

	public double getQ3() {
		return q3;
	}

	public void setQ3(double q3) {
		this.q3 = q3;
	}

	public boolean isEnabledQ3() {
		return enabledQ3;
	}

	public void setEnabledQ3(boolean enabledQ3) {
		this.enabledQ3 = enabledQ3;
	}

	public double getQ4() {
		return q4;
	}

	public void setQ4(double q4) {
		this.q4 = q4;
	}

	public boolean isEnabledQ4() {
		return enabledQ4;
	}

	public void setEnabledQ4(boolean enabledQ4) {
		this.enabledQ4 = enabledQ4;
	}

	public double getQuoteSpent() {
		return quoteSpent;
	}

	public void setQuoteSpent(double quoteSpent) {
		this.quoteSpent = quoteSpent;
	}

	public double getQuotePending() {
		return quotePending;
	}

	public void setQuotePending(double quotePending) {
		this.quotePending = quotePending;
	}

	public double getQuoteStaged() {
		return quoteStaged;
	}

	public void setQuoteStaged(double quoteStaged) {
		this.quoteStaged = quoteStaged;
	}

	public double getBudgetRemaining() {
		return budgetRemaining;
	}

	public void setBudgetRemaining(double budgetRemaining) {
		this.budgetRemaining = budgetRemaining;
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

	public String getDateQ1Enabled() {
		return dateQ1Enabled;
	}

	public void setDateQ1Enabled(String dateQ1Enabled) {
		this.dateQ1Enabled = dateQ1Enabled;
	}

	public String getDateQ2Enabled() {
		return dateQ2Enabled;
	}

	public void setDateQ2Enabled(String dateQ2Enabled) {
		this.dateQ2Enabled = dateQ2Enabled;
	}

	public String getDateQ3Enabled() {
		return dateQ3Enabled;
	}

	public void setDateQ3Enabled(String dateQ3Enabled) {
		this.dateQ3Enabled = dateQ3Enabled;
	}

	public String getDateQ4Enabled() {
		return dateQ4Enabled;
	}

	public void setDateQ4Enabled(String dateQ4Enabled) {
		this.dateQ4Enabled = dateQ4Enabled;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public List<Quote> getQuotes() {
		return quotes;
	}

	public void setQuotes(List<Quote> quotes) {
		this.quotes = quotes;
	}
	
}

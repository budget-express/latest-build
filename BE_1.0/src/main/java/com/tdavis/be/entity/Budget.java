package com.tdavis.be.entity;

import java.util.Date;
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
    private String status; //Planning-Open-Closed
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
    
    //Variables - Capex
    private double quoteSpent;
    private double quotePending;
    private double quoteStaged;
    private double budgetRemaining;
    private double budgetRequested;
    private double budgetApproved;
    
    //Variables - Opex
    private double quoteSpentOpex;
    private double quotePendingOpex;
    private double quoteStagedOpex;
    
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
    private Date dateQ1Enabled;
    private Date dateQ1Disabled;
    private Date dateQ2Enabled;
    private Date dateQ2Disabled;
    private Date dateQ3Enabled;
    private Date dateQ3Disabled;
    private Date dateQ4Enabled;
    private Date dateQ4Disabled;
    
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

	public double getQuoteSpentOpex() {
		return quoteSpentOpex;
	}

	public void setQuoteSpentOpex(double quoteSpentOpex) {
		this.quoteSpentOpex = quoteSpentOpex;
	}

	public double getQuotePendingOpex() {
		return quotePendingOpex;
	}

	public void setQuotePendingOpex(double quotePendingOpex) {
		this.quotePendingOpex = quotePendingOpex;
	}

	public double getQuoteStagedOpex() {
		return quoteStagedOpex;
	}

	public void setQuoteStagedOpex(double quoteStagedOpex) {
		this.quoteStagedOpex = quoteStagedOpex;
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

	public Date getDateQ1Enabled() {
		return dateQ1Enabled;
	}

	public void setDateQ1Enabled(Date dateQ1Enabled) {
		this.dateQ1Enabled = dateQ1Enabled;
	}

	public Date getDateQ1Disabled() {
		return dateQ1Disabled;
	}

	public void setDateQ1Disabled(Date dateQ1Disabled) {
		this.dateQ1Disabled = dateQ1Disabled;
	}

	public Date getDateQ2Enabled() {
		return dateQ2Enabled;
	}

	public void setDateQ2Enabled(Date dateQ2Enabled) {
		this.dateQ2Enabled = dateQ2Enabled;
	}

	public Date getDateQ2Disabled() {
		return dateQ2Disabled;
	}

	public void setDateQ2Disabled(Date dateQ2Disabled) {
		this.dateQ2Disabled = dateQ2Disabled;
	}

	public Date getDateQ3Enabled() {
		return dateQ3Enabled;
	}

	public void setDateQ3Enabled(Date dateQ3Enabled) {
		this.dateQ3Enabled = dateQ3Enabled;
	}

	public Date getDateQ3Disabled() {
		return dateQ3Disabled;
	}

	public void setDateQ3Disabled(Date dateQ3Disabled) {
		this.dateQ3Disabled = dateQ3Disabled;
	}

	public Date getDateQ4Enabled() {
		return dateQ4Enabled;
	}

	public void setDateQ4Enabled(Date dateQ4Enabled) {
		this.dateQ4Enabled = dateQ4Enabled;
	}

	public Date getDateQ4Disabled() {
		return dateQ4Disabled;
	}

	public void setDateQ4Disabled(Date dateQ4Disabled) {
		this.dateQ4Disabled = dateQ4Disabled;
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

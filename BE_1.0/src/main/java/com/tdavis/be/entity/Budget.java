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

	private String name;
    private String descriptionShort;
    private String descriptionLong;
    private String category;
    private String region;
    private String criticality;
    private int ranking;
    private double requested_amount;
    private double approved_amount;
    private double q1;
    private boolean q1_enabled;
    private double q2;
    private boolean q2_enabled;
    private double q3;
    private boolean q3_enabled;
    private double q4;
    private boolean q4_enabled;
    private String year;
    private double quote_spent;
    private double quote_pending;
    private double quote_staged;
	
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

	public double getRequested_amount() {
		return requested_amount;
	}

	public void setRequested_amount(double requested_amount) {
		this.requested_amount = requested_amount;
	}

	public double getApproved_amount() {
		return approved_amount;
	}

	public void setApproved_amount(double approved_amount) {
		this.approved_amount = approved_amount;
	}

	public double getQ1() {
		return q1;
	}

	public void setQ1(double q1) {
		this.q1 = q1;
	}

	public boolean isQ1_enabled() {
		return q1_enabled;
	}

	public void setQ1_enabled(boolean q1_enabled) {
		this.q1_enabled = q1_enabled;
	}

	public double getQ2() {
		return q2;
	}

	public void setQ2(double q2) {
		this.q2 = q2;
	}

	public boolean isQ2_enabled() {
		return q2_enabled;
	}

	public void setQ2_enabled(boolean q2_enabled) {
		this.q2_enabled = q2_enabled;
	}

	public double getQ3() {
		return q3;
	}

	public void setQ3(double q3) {
		this.q3 = q3;
	}

	public boolean isQ3_enabled() {
		return q3_enabled;
	}

	public void setQ3_enabled(boolean q3_enabled) {
		this.q3_enabled = q3_enabled;
	}

	public double getQ4() {
		return q4;
	}

	public void setQ4(double q4) {
		this.q4 = q4;
	}

	public boolean isQ4_enabled() {
		return q4_enabled;
	}

	public void setQ4_enabled(boolean q4_enabled) {
		this.q4_enabled = q4_enabled;
	}

	public String getYear() {
		return year;
	}

	public void setYear(String year) {
		this.year = year;
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

	public double getQuote_spent() {
		return quote_spent;
	}

	public void setQuote_spent(double quote_spent) {
		this.quote_spent = quote_spent;
	}

	public double getQuote_pending() {
		return quote_pending;
	}

	public void setQuote_pending(double quote_pending) {
		this.quote_pending = quote_pending;
	}

	public double getQuote_staged() {
		return quote_staged;
	}

	public void setQuote_staged(double quote_staged) {
		this.quote_staged = quote_staged;
	}
	

	
	
}

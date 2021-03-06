package com.redhat.demo.qlb.loan_application.model;

/**
 * This class was automatically generated by the data modeler tool.
 */

public class Applicant implements java.io.Serializable {

	static final long serialVersionUID = 1L;

	@org.kie.api.definition.type.Label("Name")
	private java.lang.String name;
	@org.kie.api.definition.type.Label("Credit Score")
	private int creditScore;

	@org.kie.api.definition.type.Label("Age")
	private int age;

	@org.kie.api.definition.type.Label("Eligible")
	private java.lang.Boolean eligible;

	@org.kie.api.definition.type.Label("Yearly Income")
	private long yearlyIncome;

	@org.kie.api.definition.type.Label("Monthly Income")
	private double monthlyIncome;

	public Applicant() {
	}

	public java.lang.String getName() {
		return this.name;
	}

	public void setName(java.lang.String name) {
		this.name = name;
	}

	public int getCreditScore() {
		return this.creditScore;
	}

	public void setCreditScore(int creditScore) {
		this.creditScore = creditScore;
	}

	public int getAge() {
		return this.age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public java.lang.Boolean getEligible() {
		return this.eligible;
	}

	public void setEligible(java.lang.Boolean eligible) {
		this.eligible = eligible;
	}

	public long getYearlyIncome() {
		return this.yearlyIncome;
	}

	public void setYearlyIncome(long yearlyIncome) {
		this.yearlyIncome = yearlyIncome;
	}

	public double getMonthlyIncome() {
		return this.monthlyIncome;
	}

	public void setMonthlyIncome(double monthlyIncome) {
		this.monthlyIncome = monthlyIncome;
	}

	public Applicant(java.lang.String name, int creditScore, int age,
			java.lang.Boolean eligible, long yearlyIncome, double monthlyIncome) {
		this.name = name;
		this.creditScore = creditScore;
		this.age = age;
		this.eligible = eligible;
		this.yearlyIncome = yearlyIncome;
		this.monthlyIncome = monthlyIncome;
	}
}
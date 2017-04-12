package com.compugain.dao;

public class ReportIssueBean {

	
	private String category;
	private String reproducibility;
	private String severity;
	private String priority;
	private String assignTo;
	private String summary;
	private String description;
	
	
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getReproducibility() {
		return reproducibility;
	}
	public void setReproducibility(String reproducibility) {
		this.reproducibility = reproducibility;
	}
	public String getSeverity() {
		return severity;
	}
	public void setSeverity(String severity) {
		this.severity = severity;
	}
	public String getPriority() {
		return priority;
	}
	public void setPriority(String priority) {
		this.priority = priority;
	}
	public String getAssignTo() {
		return assignTo;
	}
	public void setAssignTo(String assignTo) {
		this.assignTo = assignTo;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getStepstoreproduce() {
		return stepstoreproduce;
	}
	public void setStepstoreproduce(String stepstoreproduce) {
		this.stepstoreproduce = stepstoreproduce;
	}
	private String stepstoreproduce;
	
}

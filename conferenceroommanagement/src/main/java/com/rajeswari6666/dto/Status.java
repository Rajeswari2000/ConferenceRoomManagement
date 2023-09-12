package com.rajeswari6666.dto;

public class Status {
	private int statusCode;
	private String errorType;
	private String description;
	
	public int getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}
	public String getErrorType() {
		return errorType;
	}
	public void setErrorType(String errorType) {
		this.errorType = errorType;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public Status(int statusCode, String errorType, String description) {
		super();
		this.statusCode = statusCode;
		this.errorType = errorType;
		this.description = description;
	}
	
	
	
	
}

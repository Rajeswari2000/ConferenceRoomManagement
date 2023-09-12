package com.rajeswari6666.dto;

public class Employee {
   private String name;
   private String email;
   private String floor; 
   
   
public String getName() {
	return name;
}
public void setName(String name) {
	this.name = name;
}
public String getEmail() {
	return email;
}
public void setEmail(String email) {
	this.email = email;
}
public String getFloor() {
	return floor;
}
public void setFloor(String floor) {
	this.floor = floor;
}

public Employee(String name, String email, String floor) {
	super();
	this.name = name;
	this.email = email;
	this.floor = floor;
}

}

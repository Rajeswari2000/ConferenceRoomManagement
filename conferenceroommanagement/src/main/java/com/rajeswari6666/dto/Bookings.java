package com.rajeswari6666.dto;

import java.sql.Time;

public class Bookings {
	private int bookingId;
	private int roomId;
	private String email;
	private Time startTime;
	private Time endTime;
	private String floor;
	
	public String getFloor() {
		return floor;
	}

	public void setFloor(String floor) {
		this.floor = floor;
	}

	public Bookings(int bookingId, int roomId, String email, Time startTime, Time endTime, String floor) {
		super();
		this.bookingId = bookingId;
		this.roomId = roomId;
		this.email = email;
		this.startTime = startTime;
		this.endTime = endTime;
		this.floor = floor;
	}

	public int getBookingId() {
		return bookingId;
	}

	public void setBookingId(int bookingId) {
		this.bookingId = bookingId;
	}

	public int getRoomId() {
		return roomId;
	}

	public void setRoomId(int roomId) {
		this.roomId = roomId;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Time getStartTime() {
		return startTime;
	}

	public void setStartTime(Time startTime) {
		this.startTime = startTime;
	}

	public Time getEndTime() {
		return endTime;
	}

	public void setEndTime(Time endTime) {
		this.endTime = endTime;
	}
	
	
	
}

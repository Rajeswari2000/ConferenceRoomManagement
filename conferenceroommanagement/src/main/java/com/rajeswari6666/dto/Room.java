package com.rajeswari6666.dto;

public class Room {
	private int roomId;
	private String floor;
	private int capacity;
	
	
	public int getRoomId() {
		return roomId;
	}
	public void setRoomId(int roomId) {
		this.roomId = roomId;
	}
	public String getFloor() {
		return floor;
	}
	public void setFloor(String floor) {
		this.floor = floor;
	}
	public int getCapacity() {
		return capacity;
	}
	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}
	
	
	public Room(int roomId, String floor, int capacity) {
		super();
		this.roomId = roomId;
		this.floor = floor;
		this.capacity = capacity;
	}
	
	
	
}

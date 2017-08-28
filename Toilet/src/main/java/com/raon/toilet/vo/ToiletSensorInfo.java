package com.raon.toilet.vo;

public class ToiletSensorInfo {
	private String sensorId;
	private int floor;
	private int contailner;
	private String status;
	private int gender;
	
	
	
	public String getSensorId() {
		return sensorId;
	}
	public void setSensorId(String sensorId) {
		this.sensorId = sensorId;
	}
	public int getFloor() {
		return floor;
	}
	public void setFloor(int floor) {
		this.floor = floor;
	}
	public int getContailner() {
		return contailner;
	}
	public void setContailner(int contailner) {
		this.contailner = contailner;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public int getGender() {
		return gender;
	}
	public void setGender(int gender) {
		this.gender = gender;
	}
	
	
	public String toString() {
		return "sensorId=" + sensorId
			+ "floor=" + floor
			+ "contailner=" + contailner
			+ "status=" + status
			+ "gender=" + gender;
	}
}

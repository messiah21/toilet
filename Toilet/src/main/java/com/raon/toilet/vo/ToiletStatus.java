package com.raon.toilet.vo;

public class ToiletStatus {
	private int floor;
	private int container;
	private String status;
	private String sensorId;
	private String udt;
	private String useId;
	private int gender;
	
	
	public int getFloor() {
		return floor;
	}
	public void setFloor(int floor) {
		this.floor = floor;
	}
	public int getContainer() {
		return container;
	}
	public void setContainer(int container) {
		this.container = container;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getSensorId() {
		return sensorId;
	}
	public void setSensorId(String sensorId) {
		this.sensorId = sensorId;
	}
	public String getUdt() {
		return udt;
	}
	public void setUdt(String udt) {
		this.udt = udt;
	}
	public String getUseId() {
		return useId;
	}
	public void setUseId(String useId) {
		this.useId = useId;
	}
	public int getGender() {
		return gender;
	}
	public void setGender(int gender) {
		this.gender = gender;
	}
	
	public String toString() {
		return "floor=" + floor
			+ "container=" + container
			+ "sensorId=" + sensorId
			+ "status=" + status
			+ "udt=" + udt
			+ "useId=" + useId
			+ "gender=" + gender;
	}
	
}

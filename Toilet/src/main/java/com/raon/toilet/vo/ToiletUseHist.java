package com.raon.toilet.vo;

public class ToiletUseHist {
	private String useId;
	private String sensorId;
	private int floor;
	private int contailner;
	private String startDt;
	private String endDt;
	private int gender;
	
	
	public String getUseId() {
		return useId;
	}
	public void setUseId(String useId) {
		this.useId = useId;
	}
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
	public String getStartDt() {
		return startDt;
	}
	public void setStartDt(String startDt) {
		this.startDt = startDt;
	}
	public String getEndDt() {
		return endDt;
	}
	public void setEndDt(String endDt) {
		this.endDt = endDt;
	}
	public int getGender() {
		return gender;
	}
	public void setGender(int gender) {
		this.gender = gender;
	}
	
	public String toString() {
		return "useId=" + useId
			+ "sensorId=" + sensorId
			+ "floor=" + floor
			+ "contailner=" + contailner
			+ "startDt=" + startDt
			+ "endDt=" + startDt
			+ "gender=" + gender;
	}
	

}

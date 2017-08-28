package com.raon.toilet.service;

import java.util.List;

import com.raon.toilet.vo.ToiletStatus;

import net.sf.json.JSONObject;

public interface ToiletStatusService {
	public List<ToiletStatus> selectToiletStatusInfo(String floor, String container, String sensorId, String gender);
	public JSONObject updateToiletStatusInfo(String sensorId, int toiletStatus, String useId, int gender);
	public JSONObject changeToiletStatusInfo(String sensorId, int toiletStatus, int gender);
}

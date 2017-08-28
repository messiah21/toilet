package com.raon.toilet.dao;

import java.util.List;

import com.raon.toilet.vo.ToiletStatus;

public interface ToiletStatusDao {
	public List<ToiletStatus> selectToiletStatusInfo(String floor, String container, String sensorId, String gender) throws Exception;
	public int updateToiletStatusInfo(String sensorId, int toiletStatus, String updateTime, String useId, int gender) throws Exception;
}

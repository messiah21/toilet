package com.raon.toilet.dao;

import com.raon.toilet.vo.ToiletSensorInfo;

public interface ToiletSensorInfoDao {
	ToiletSensorInfo selectToiletSensorInfo(String sensorId) throws Exception;
}

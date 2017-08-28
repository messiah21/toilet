package com.raon.toilet.dao;

import com.raon.toilet.vo.ToiletUseHist;

public interface ToiletUseHistDao {
	public ToiletUseHist selectToiletUseHist(String floor, String container, String sensorId, String useId) throws Exception;
	public int insertToiletUseHist(String sensorId) throws Exception;
	public int updateToiletUseHistEndDt(String sensorId, String useId, String endDt) throws Exception;
}

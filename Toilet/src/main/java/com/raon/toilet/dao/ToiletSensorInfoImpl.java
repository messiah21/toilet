package com.raon.toilet.dao;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.raon.toilet.common.dao.CommonDao;
import com.raon.toilet.vo.ToiletSensorInfo;

@Repository("toiletSensorInfoDao")
public class ToiletSensorInfoImpl extends CommonDao implements ToiletSensorInfoDao {

	public ToiletSensorInfo selectToiletSensorInfo(String sensorId) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("sensorId", sensorId);
		return this.getSqlSession().selectOne("toliet.status.info.selectSensorInfo", map);
		
	}

}

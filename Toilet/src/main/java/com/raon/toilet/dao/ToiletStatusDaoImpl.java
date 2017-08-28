package com.raon.toilet.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.raon.toilet.common.dao.CommonDao;
import com.raon.toilet.vo.ToiletStatus;


@Repository("toiletStatusDao")
public class ToiletStatusDaoImpl extends CommonDao implements ToiletStatusDao {

	public List<ToiletStatus> selectToiletStatusInfo(String floor, String container, String sensorId, String gender) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("floor", floor);
		map.put("container", container);
		map.put("sensorId", sensorId);
		map.put("gender", gender);
		return this.getSqlSession().selectList("toilet.status.info.selectToiletStatus", map);
	}

	public int updateToiletStatusInfo(String sensorId, int toiletStatus, String updateTime, String useId, int gender) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("sensorId", sensorId);
		map.put("toiletStatus", String.valueOf(toiletStatus));
		map.put("updateTime", String.valueOf(updateTime));
		map.put("useId", useId);
		map.put("gender", String.valueOf(gender));
		return this.getSqlSession().insert("toilet.status.info.updateToiletStatus", map);
	}

}

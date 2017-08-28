package com.raon.toilet.dao;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.raon.toilet.common.dao.CommonDao;
import com.raon.toilet.vo.ToiletUseHist;


@Repository("toiletUseHistDao")
public class ToiletUseHistDaoImpl extends CommonDao implements ToiletUseHistDao {

	public ToiletUseHist selectToiletUseHist(String floor, String container, String sensorId, String useId)
			throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("floor", floor);
		map.put("container", container);
		map.put("sensorId", sensorId);
		map.put("useId", useId);
		return this.getSqlSession().selectOne("toilet.use.hist.Info.selectToiletUseHist", map);
	}

	public int insertToiletUseHist(String sensorId) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("sensorId", sensorId);
		return this.getSqlSession().insert("toilet.use.hist.Info.insertToiletUseHist", map);
	}

	public int updateToiletUseHistEndDt(String sensorId, String useId, String endDt) throws Exception {
		Map<String, String> map = new HashMap<String, String>();
		map.put("sensorId", sensorId);
		map.put("useId", useId);
		map.put("endDt", endDt);
		return this.getSqlSession().update("toilet.use.hist.Info.updateToiletUseHistEndDt", map);
	}

}

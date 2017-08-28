package com.raon.toilet.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;

import com.raon.toilet.common.keygen.RandomKeyGenerator;
import com.raon.toilet.common.service.CommonService;
import com.raon.toilet.common.util.DateUtil;
import com.raon.toilet.common.util.StringUtil;
import com.raon.toilet.common.util.TxUtil;
import com.raon.toilet.constant.CommonConstant;
import com.raon.toilet.dao.ToiletStatusDao;
import com.raon.toilet.dao.ToiletUseHistDao;
import com.raon.toilet.vo.ToiletStatus;

import net.sf.json.JSONObject;

@Service("toiletStatusService")
public class ToiletStatusServiceImpl extends CommonService implements ToiletStatusService {
	protected transient Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	public ToiletStatusDao toiletStatusDao;

	@Autowired
	public ToiletUseHistDao toiletUseHistDao;
	
	@Autowired
	public TxUtil txUtil;
	
	
	public List<ToiletStatus> selectToiletStatusInfo(String floor, String container, String sensorId, String gender) {
		List<ToiletStatus> toiletStatusInfoList = null;
		try {
			toiletStatusInfoList = toiletStatusDao.selectToiletStatusInfo(floor, container, sensorId, gender);
		} catch (Exception e) {
			logger.error("select toilet status error :" + e.getMessage());
		}
		
		return toiletStatusInfoList;
	}
	
	public JSONObject changeToiletStatusInfo(String sensorId, int toiletStatus, int gender) {
		JSONObject resultJson = null;
		TransactionStatus status = null;
		int result = 0;
		
		try {
			List<ToiletStatus> toiletStatusInfoList = selectToiletStatusInfo("", "", sensorId, "");
			String useId = toiletStatusInfoList.get(0).getUseId();
			
			status = txUtil.getTransaction();
			
			if( toiletStatus == 0 ) {
				resultJson = updateToiletStatusInfo(toiletStatusInfoList.get(0).getSensorId(), toiletStatus, "", gender);
				
				if( StringUtil.isNull(useId) ) {
				} else {
					if( resultJson.get("resultCode").equals(CommonConstant.RESULT_CODE_SUCCESS) ) {
						String endTime = new DateUtil().getDateNTimeByForm("yyyyMMddHHmmss");
						result = toiletUseHistDao.updateToiletUseHistEndDt(sensorId, useId, endTime);
					} else {
						result = -1;
					}
				}
			} else {
				String newUserId = new RandomKeyGenerator().nextSeqNumberKey();
				
				resultJson = updateToiletStatusInfo(toiletStatusInfoList.get(0).getSensorId(), toiletStatus, newUserId, gender);
				
				if( StringUtil.isNull(useId) ) {
				} else {
					String endTime = new DateUtil().getDateNTimeByForm("yyyyMMddHHmmss");
					result = toiletUseHistDao.updateToiletUseHistEndDt(sensorId, useId, endTime);
				}
				
				if( resultJson.get("resultCode").equals(CommonConstant.RESULT_CODE_SUCCESS) ) {
					result = toiletUseHistDao.insertToiletUseHist(sensorId);
				} else {
					result = -1;
				}
			}
//			
//			// toilet status 테이블에서 sensorId로 정보를 가져와서 useId가 없을 경우   
//			// toilet use history 테이블에 insert, 있을 경우 update
//			if( StringUtil.isNull(useId) ) {
//				String newUserId = new RandomKeyGenerator().nextSeqNumberKey();
//				
//				resultJson = updateToiletStatusInfo(toiletStatusInfoList.get(0).getSensorId(), toiletStatus, newUserId);
//				if( resultJson.get("resultCode").equals(CommonConstant.RESULT_CODE_SUCCESS) ) {
//					result = toiletUseHistDao.insertToiletUseHist(sensorId);
//				} else {
//					result = -1;
//				}
//			} else {
//				resultJson = updateToiletStatusInfo(toiletStatusInfoList.get(0).getSensorId(), toiletStatus, "");
//				if( resultJson.get("resultCode").equals(CommonConstant.RESULT_CODE_SUCCESS) ) {
//					String endTime = new DateUtil().getDateNTimeByForm("yyyyMMddHHmmss");
//					result = toiletUseHistDao.updateToiletUseHistEndDt(sensorId, useId, endTime);
//				} else {
//					result = -1;
//				}
//			}
		} catch (Exception e) {
			logger.error("change toilet status error :" + e.getMessage());
			result = -1;
		}
		
		
		if( result <= 0 ) {
			txUtil.rollBackTransaction(status);
		} else {
			txUtil.commitTransaction(status);
		}
		
		return resultJson;
		
	}
	
	

	public JSONObject updateToiletStatusInfo(String sensorId, int toiletStatus, String useId, int gender) {
		JSONObject resultJson = new JSONObject();
		String updateTime = new DateUtil().getDateNTimeByForm("yyyyMMddHHmmss");
		int result = 0;
		
		try {
			result = toiletStatusDao.updateToiletStatusInfo(sensorId, toiletStatus, updateTime, useId, gender);
		} catch (Exception e) {
			logger.error("update toilet status error :" + e.getMessage());
			result = -1;
		}
		
		if( result > 0 ) {
			resultJson.put("resultCode", CommonConstant.RESULT_CODE_SUCCESS);
		} else {
			resultJson.put("resultCode", CommonConstant.RESULT_CODE_FAIL);
		}
		
		return resultJson;
	}
}

package com.raon.toilet.controller.sensor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.raon.toilet.common.util.CommonUtil;
import com.raon.toilet.constant.CommandConstant;
import com.raon.toilet.service.ToiletStatusService;

import net.sf.json.JSONObject;

@Controller
@RequestMapping("/sensor")
public class ToiletSensorController {
	protected transient Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private ToiletStatusService toiletStatusService;
	
	
	@RequestMapping(method=RequestMethod.POST, value = "/request")
	public void processRequest(HttpServletRequest req, HttpServletResponse resp ) {
		
		logger.info("===================== START SENSOR REQUEST =====================");

		String strRequest = new CommonUtil().readInputData(req);

		logger.info("[SENSOR REQ] Request Message:"+strRequest);
		
		
		JSONObject jsonRequest = JSONObject.fromObject(strRequest);
		JSONObject jsonResult = null;
		
		int cmdType = jsonRequest.getInt("cmdType");
		String sensorId = jsonRequest.getString("sensorId");
		int toiletStatus = jsonRequest.getInt("status");
		int gender = jsonRequest.getInt("gender");
		
		switch(cmdType) {
		case CommandConstant.CHECK :
			jsonResult = toiletStatusService.updateToiletStatusInfo(sensorId, toiletStatus, null, gender);
			// 상태 체크 요청
			break;
		case CommandConstant.CHANGE :
			// 상태 변경 요청
			jsonResult = toiletStatusService.changeToiletStatusInfo(sensorId, toiletStatus, gender);
			break;
		}
		new CommonUtil().getResultJsonObject(jsonResult, req, resp);
		
		return;
	}
	
	public static void main(String[] args) {
		
		String strJSon = "{\"cmdType\" : 0,\"sensorId\" : \"a1asdasdasdasd\",\"status\" : \"0\"}";
		
		JSONObject jsonObj = JSONObject.fromObject(strJSon);
		System.out.println(jsonObj.toString());
		System.out.println(jsonObj.get("cmdType"));
		
		System.out.println(String.valueOf(null));
		
	}
}

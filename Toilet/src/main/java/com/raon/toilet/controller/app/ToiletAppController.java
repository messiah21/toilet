package com.raon.toilet.controller.app;

import java.util.List;

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
import com.raon.toilet.vo.ToiletStatus;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

@Controller
@RequestMapping("/app")
public class ToiletAppController {
	protected transient Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private ToiletStatusService toiletStatusService;
	
	@RequestMapping(method=RequestMethod.POST, value = "/reqToiletStatusList")
	public void processRequest(HttpServletRequest req, HttpServletResponse resp ) {
		
		logger.info("===================== START APP REQUEST =====================");

		String strRequest = new CommonUtil().readInputData(req);

		logger.info("[APP REQ] Request Message:"+strRequest);
		
		JSONObject jsonRequest = JSONObject.fromObject(strRequest);
		JSONObject jsonResult = null;
		JSONArray toiletInfoJSONArray = new JSONArray(); 
		
		
		int cmdType = jsonRequest.getInt("cmdType");
		String gender = jsonRequest.getString("gender");
		
		List<ToiletStatus> toiletStatusInfoList = toiletStatusService.selectToiletStatusInfo("", "", "", gender);
		
		for(ToiletStatus tmpToiletStatusInfo : toiletStatusInfoList) {
			JSONObject token = new JSONObject();
			token.put("floor", tmpToiletStatusInfo.getFloor());
			token.put("container", tmpToiletStatusInfo.getContainer());
			token.put("status", tmpToiletStatusInfo.getStatus());
			token.put("gender", tmpToiletStatusInfo.getGender());
			
			toiletInfoJSONArray.add(token);
		}
		
		jsonResult = new JSONObject();
		jsonResult.put("resultCode", "0000");
		jsonResult.put("toiletList", toiletInfoJSONArray);
		
		logger.debug(jsonResult.toString());
		
		new CommonUtil().getResultJsonObject(jsonResult, req, resp);
		
		return;
	}
	
}

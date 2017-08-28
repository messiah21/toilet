package com.raon.toilet.common.service;

import java.io.OutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

import com.raon.toilet.common.util.TxUtil;

import net.sf.json.JSONObject;


public class CommonService {
	@Autowired
	protected TxUtil txUtil;
	
	
	public void getResultJsonObject(JSONObject jsonObj,HttpServletRequest req,HttpServletResponse res){
		this.getResultJsonObject(jsonObj, req, res , 1);
	}
		
	private void getResultJsonObject(JSONObject jsonObj,HttpServletRequest req,HttpServletResponse res, int contentType) {
		this.getResultJsonObject(jsonObj.toString(), req, res, contentType);
	}
	
	public void getResultJsonObject(String strData,HttpServletRequest req,HttpServletResponse res, int contentType) {
		switch(contentType){
			case 1:  res.setContentType("application/json; charset=utf-8"); break;
			default: res.setContentType("text/xml;charset=utf-8"); break;
		}
		res.setHeader("Cache-Control", "no-cache");

		OutputStream output;
		try {
			output = res.getOutputStream();
			output.write(strData.getBytes("UTF-8"));
			output.flush();
		} catch (Exception e) {
			
		}
	}
}

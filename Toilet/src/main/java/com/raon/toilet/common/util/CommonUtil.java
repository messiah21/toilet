package com.raon.toilet.common.util;

import java.io.BufferedReader;
import java.io.OutputStream;
import java.net.URLDecoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.sf.json.JSONObject;

public class CommonUtil {
	protected transient Logger logger = LoggerFactory.getLogger(this.getClass());
	
	public String readInputData(HttpServletRequest req) {
		StringBuffer sb = new StringBuffer();
		BufferedReader reader;
		String line;
		try {
			reader = req.getReader();
			while ((line = reader.readLine()) != null) {
				sb.append(line);
			}
			if(sb.toString().trim().length()==0){
				String str = req.getQueryString();
				if(str != null && str.trim().length() > 0){
					sb.append(URLDecoder.decode(req.getQueryString(), req.getCharacterEncoding()));
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return sb.toString();
	}
	

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

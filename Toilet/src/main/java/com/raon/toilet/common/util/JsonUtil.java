package com.raon.toilet.common.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import net.sf.json.util.JSONUtils;

public class JsonUtil {

	public JsonUtil(){}
	
	public String getString(JSONObject jsonObject, String key) {
		String str = null;
		try {
			str = jsonObject.getString(key);
		} catch (JSONException e) {
			str = "";
		}
		return str;
	}
	
	public JSONArray getArray(JSONObject jsonObject, String key) {
		JSONArray jsonArray = null;
		try {
			jsonArray = jsonObject.getJSONArray(key);
		} catch (JSONException e) {
			jsonArray = new JSONArray();
		}
		return jsonArray;
	}	

	
	/**
	 * <pre>
	 * JSON 데이터를 Map으로 변경해 반환한다
	 * JSONArray는 Map을 가지는 List로 변경한다.
	 * </pre>
	 * <pre>
	 * <b>Parameters:</b>
	 * ▶
	 * ▶
	 * </pre>
	 * <pre>
	 * <b>Returns:</b>
	 * 
	 * </pre>
	 * @author HJKIM
	 */
	public Map<String,?> convertMap(JSONObject jsonObject) {
		Map map = new HashMap();
		Iterator<String> iterator = jsonObject.keys();
		String key = null;
		while(iterator.hasNext()){
			key = iterator.next();
			if(JSONUtils.isNull(jsonObject.get(key))){
				map.put(key,null);
			}
			else if(JSONUtils.isNumber(jsonObject.get(key))){
				map.put(key,jsonObject.getString(key));
			}
			else if(JSONUtils.isString(jsonObject.get(key))){
				map.put(key,jsonObject.getString(key));
			}
			else if(JSONUtils.isArray(jsonObject.get(key))){
				JSONArray jsonArray = jsonObject.getJSONArray(key);
				if(JSONUtils.isArray(jsonArray) && jsonArray.size() > 0){
					if(JSONUtils.isString(jsonArray.get(0))){
						map.put(key,this.convertListString(jsonObject.getJSONArray(key)));
					}
					else{
						map.put(key,this.convertList(jsonObject.getJSONArray(key)));
					}
				}
				else{
					map.put(key,this.convertList(jsonObject.getJSONArray(key)));
				}
			}else if(JSONUtils.isObject(jsonObject.get(key))){
				map.put(key,this.convertMap(jsonObject.getJSONObject(key)));
			}
		}
		return map;
	}
	
	
	public Map<String,?> convertMap(String jsonString){
		return this.convertMap(JSONObject.fromObject(jsonString));
	}
	
	
	private List<Map<String,?>> convertList(JSONArray jsonArray){
		List<Map<String,?>> list = new ArrayList<Map<String,?>>();
		if(JSONUtils.isNull(jsonArray)){
			return list;
		}
		for(int i=0; i< jsonArray.size(); i++){
			try{
				list.add(this.convertMap(jsonArray.getJSONObject(i)));
			}
			catch(JSONException ex){
				list.add(this.convertMap(jsonArray.getJSONArray(i).getJSONObject(0)));
			}
		}
		return list;
	}
	
	private List<String> convertListString(JSONArray jsonArray) {
		List<String> list = new ArrayList<String>();
		if(JSONUtils.isNull(jsonArray)){
			return list;
		}
		for(int i=0; i< jsonArray.size(); i++){
			String val = jsonArray.getString(i);
			list.add(val);
		}
		return list;
	}	

}

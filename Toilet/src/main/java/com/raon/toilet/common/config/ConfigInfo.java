/**
 * 
 */
package com.raon.toilet.common.config;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.configuration.CompositeConfiguration;

/**
 * @author HJKIM
 *
 */
public class ConfigInfo {

	private static ConfigInfo instance = new ConfigInfo();
	private List<String> configList = new ArrayList<String>();
	private CompositeConfiguration compositeConfig;
	private ToiletConfiguration toiletConfig;
	
	private ConfigInfo(){}
	
	public static ConfigInfo getInstance() {
		if(instance == null){
			instance = new ConfigInfo();
		}
		return instance;
	}
	
	public void addConfig(String fileName){
		this.configList.add(fileName);
	}
	
	public List<String> getConfigFileNameList(){
		return this.configList;
	}
	
	public void clearConfig(){
		this.configList.clear();
		this.compositeConfig = null;
		this.toiletConfig = null;
	}

	
	public CompositeConfiguration getCompositeConfig() {
		return compositeConfig;
	}

	
	public void setCompositeConfig(CompositeConfiguration compositeConfig) {
		this.toiletConfig = new ToiletConfiguration(compositeConfig);
		this.compositeConfig = compositeConfig;
	}
	
	public ToiletConfiguration getToiletConfiguration(){
		return this.toiletConfig;
	}	
	
}

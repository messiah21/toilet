/**
 * 
 */
package com.raon.toilet.common.util;

import java.io.File;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;

import org.apache.log4j.DailyRollingFileAppender;
import org.apache.log4j.Layout;

import com.raon.toilet.common.config.ConfigInfo;


/**
 * @author HJKIM
 *
 */
public class ExDailyRollingFileAppender extends DailyRollingFileAppender{
	
	public ExDailyRollingFileAppender(){
		super();
    }
    
	public ExDailyRollingFileAppender(Layout layout, String fileName, String datePattern)
            throws IOException{
    	super(layout,changeFilePath(fileName),datePattern);
    }
	
	public void setFile(String fileName){
		super.setFile(changeFilePath(fileName));
	}
	
	private static String changeFilePath(String fileName){
		return getLogDirPath()+"/"+fileName;
	}
	
	
	private static String getLogDirPath(){
		
		ConfigInfo configInfo = ConfigInfo.getInstance();
		String dirPath = configInfo.getToiletConfiguration().getLogDir();
		String type = configInfo.getToiletConfiguration().getLogDirAppendType();
		
		try{
		  InetAddress localHost = Inet4Address.getLocalHost();
	      String ipAddress = localHost.getHostAddress();
	      String hostName = localHost.getHostName();
	      
	      if("0".equals(type)){
	    	  dirPath += "";
	      }
	      else if("1".equals(type)){
	    	  dirPath += "/"+hostName;
	      }
	      else if("2".equals(type)){
	    	  dirPath += "/"+ipAddress;
	      }
	      System.out.println("logDirPath:"+dirPath);
	      
	      File dir = new File(dirPath);
	      if(!dir.exists()){
	    	  System.out.println("mkdirs:"+dirPath);
	    	  dir.mkdirs();
	      }
	      
		}catch(Exception e){
			System.out.println(e.getMessage());
		}
		return dirPath;
	}
	
}

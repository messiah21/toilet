/**
 * 
 */
package com.raon.toilet.servlet.context;

import java.io.File;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;

import org.apache.commons.configuration.CompositeConfiguration;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.springframework.web.util.Log4jConfigListener;
import org.springframework.web.util.Log4jWebConfigurer;

import com.raon.toilet.common.config.ConfigInfo;


/**
 * @author HJKIM
 *
 */
public class ToiletLog4jConfigListener extends Log4jConfigListener{
	
	   public void contextInitialized(ServletContextEvent event){
	    	System.out.println("*******OnepassLog4jConfigListener contextInitialized");
	    	try {
	        	ConfigInfo configInfo = ConfigInfo.getInstance();
		    	String logsDir = configInfo.getToiletConfiguration().getLogDir(); 
			    if(logsDir == null || logsDir.equals("")){
			    	this.configFileLoad(event);
			    }
			}catch(Exception e){
	    		System.out.println(e.getMessage());
	    	}
			Log4jWebConfigurer.initLogging(event.getServletContext());
	    }
	    
		/**
		 * <pre>
		 * 설정파일을 로드한다. 
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
		private void configFileLoad(ServletContextEvent event){
			
	    	try {
				ServletContext servletContext =  event.getServletContext();
				CompositeConfiguration config = new CompositeConfiguration();
				
				//================================ 설정파일 기준 경로 잡기 =================================
				//web.xml설정에서 configuration.properties 클래스 path경로값을 가져온다.
				String location = servletContext.getInitParameter("basicConfigFile");
				location = location.replaceAll("\\\\","/");
				System.out.println("basicConfig:"+location);
				
				//마지막파일명을 제외한 index
				int nameIndex = location.lastIndexOf("/");
				//파일명을 뺀 클래스path경로
				String classPath = "";
				if(nameIndex != -1){
					//config/onepass
					classPath = location.substring(0, nameIndex);
				}
				//===============================================================================
				
				
				//============================= onepass설정파일 셋팅  ===================================
				//basicConfigFile 파일의 절대경로를 구한다. 
				PropertiesConfiguration proc = new PropertiesConfiguration(location);
				String realPath = proc.getPath();
				realPath = realPath.replaceAll("\\\\","/");
				System.out.println("basicConfig realPath1:"+realPath);
				String realPathDir = "";
				nameIndex = realPath.lastIndexOf("/");
				if(nameIndex != -1){
					realPathDir = realPath.substring(0, nameIndex);
				}
				File onepassConfigDir = new File(realPathDir);
	        	this.setPropertyConfig(config, onepassConfigDir, classPath);
	        	//=================================================================================
	        	
	        	//================= 외부설정파일 셋팅(classpath 루트디렉토리에 위치시 외부설정파일로 간주) ==================
	        	String classRealPathDir = realPath.substring(0,realPath.length()-location.length());
	    		System.out.println("externalConfig classPath:"+classRealPathDir);
	    		File externalConfigDir = new File(classRealPathDir);
	    		this.setPropertyConfig(config, externalConfigDir, "");
	    		//=================================================================================
	    		
	    		ConfigInfo configInfo = ConfigInfo.getInstance();
	        	configInfo.setCompositeConfig(config);
	        	
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}	
		
		
		private void setPropertyConfig(CompositeConfiguration config, File configDirectory, String classPath){
			try{
				if(configDirectory.exists() && configDirectory.isDirectory()){
					ConfigInfo configInfo = ConfigInfo.getInstance();
		    		PropertiesConfiguration configuration = null;
		    		for(File configFile : configDirectory.listFiles()){
		    			int index = (configFile.getName().lastIndexOf(".") == -1) ? 0 : configFile.getName().lastIndexOf(".");
		    			String extension = configFile.getName().substring(index);
		    			if(extension != null && extension.toLowerCase().equals(".properties")){
		    				System.out.println(configFile.getName());
		        			configInfo.addConfig(configFile.getName());
		        			configuration = new PropertiesConfiguration(classPath+"/"+configFile.getName());
		        			config.addConfiguration(configuration);
		    			}
		    		}
		    	}
			}catch(Exception e){
				System.out.println(e.getMessage());
			}
		}     

}

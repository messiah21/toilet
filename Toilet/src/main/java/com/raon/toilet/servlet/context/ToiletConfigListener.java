package com.raon.toilet.servlet.context;

import java.io.File;
import java.security.Security;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.configuration.CompositeConfiguration;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.spongycastle.jce.provider.BouncyCastleProvider;

import com.raon.toilet.common.config.ConfigInfo;


/**
 * @author HJKIM
 *
 */
public class ToiletConfigListener implements ServletContextListener{

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
	
	/**
	 * <pre>
	 * Security provider에 SpongyCastle을 동적으로 추가한다.
	 * -> 서버 reload시 BC 관련 예외(java.security.spec.InvalidKeySpecException: key spec not recognised) 발생
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
	 * @author JJLIM
	 */
	private void reloadSecurityProvider(ServletContextEvent event){
		
		try {
			BouncyCastleProvider bcProvider = new BouncyCastleProvider();
	        Security.removeProvider(bcProvider.getName()); // remove old instance
			Security.insertProviderAt(new org.spongycastle.jce.provider.BouncyCastleProvider(), 11);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	
	public void contextInitialized(ServletContextEvent event) {
		System.out.println("ToiletConfigListener contextInitialized");

		this.reloadSecurityProvider(event);
		this.configFileLoad(event);
	}
	
	public void contextDestroyed(ServletContextEvent event) {
		System.out.println("ToiletConfigListener contextDestroyed");
		
		ConfigInfo configInfo = ConfigInfo.getInstance();
		configInfo.clearConfig();
	}
}

/**
 * 
 */
package com.raon.toilet.servlet.handler;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

/**
 * @author HJKIM
 *
 */
public class ToiletInterceptor extends HandlerInterceptorAdapter {

	protected transient Logger logger = LoggerFactory.getLogger(this.getClass());	
	protected HashMap<String, Class<?>> clazzs = new HashMap<String, Class<?>>();	

	
	
	@Override
	public boolean preHandle(HttpServletRequest req, HttpServletResponse res, Object handler) throws Exception {
		
		String requestURI = req.getRequestURI();	
		boolean isPass = false;
		
		//호출되는 URI API정보를 찍는다.
		Class<?> cls = getClazz(handler.getClass().getName().split("\\$")[0]);
		logger.debug("########## Call Controller: "+cls.getSimpleName()+" ##########");
		logger.debug("########## RequestURI: "+ requestURI+" ##########");
		Method[] meths =  cls.getMethods();
		for(int i=0; i < meths.length; i++){
			String mName = meths[i].getName();
			Annotation[] ans = meths[i].getDeclaredAnnotations();
			for(int j=0; j < ans.length; j++){
				if(ans[j].toString().indexOf(requestURI) > -1){
					logger.debug("########## Call Method: "+mName+" ##########");
					break;
				}
			}
		}	
		isPass = true;
		return isPass;
	}
	
    @Override
    public void postHandle(HttpServletRequest request,
               HttpServletResponse response, Object handler,
               ModelAndView modelAndView) throws Exception {
    }
    
	private Class<?> getClazz(String name) throws ClassNotFoundException{
		synchronized (clazzs) {
			Class<?> clazz = (Class<?>) clazzs.get(name);
			if(clazz == null){
				clazz = Class.forName(name);
				clazzs.put(clazz.getSimpleName(),clazz);
			}
			return clazz;
		}
	}  
	
	

	
}

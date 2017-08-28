/**
 * 
 */
package com.raon.toilet.common.jndi;
import org.springframework.jndi.JndiObjectFactoryBean;

/**
 * @author HJKIM
 *
 */
public class ExJndiObjectFactoryBean extends JndiObjectFactoryBean{
	
	public ExJndiObjectFactoryBean(){
		super();
	}
	
	public void setUseYn(String useYn){
		if("N".equals(useYn)){
			super.setLookupOnStartup(false);
		}
	}
	
	public void setWasType(String wasType){
		String jndiName = super.getJndiName();
		if("1".equals(wasType)){
			jndiName = "java:comp/env/"+jndiName;
		}
		else if("2".equals(wasType)){
			
		}
		else if("3".equals(wasType)){
			
		}
		else if("4".equals(wasType)){
			
		}
		else{
			
		}
		super.setJndiName(jndiName);
	}
	
	

	
}

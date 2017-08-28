package com.raon.toilet.common.config;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletContext;

import org.apache.commons.configuration.CompositeConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.raon.toilet.common.util.StringUtil;


public class ToiletConfiguration extends CompositeConfiguration {
	
    @Autowired
    private ServletContext servletContext;
    
	protected transient Logger logger = LoggerFactory.getLogger(this.getClass());
    
	public ToiletConfiguration(){
		ConfigInfo configInfo = ConfigInfo.getInstance();
		super.append(configInfo.getCompositeConfig());
	}
	
	public ToiletConfiguration(CompositeConfiguration compositeConfig){
		super.append(compositeConfig);
	}

	public String getProjectBaseDir(){
		return getString("toilet.project.base");
	}
	
	public String getLogDir() {
		return getString("toilet.logs.dir");
	}
	
	public String getLogDirAppendType() {
		return getString("toilet.logs.dir.append.type");
	}
	
	public boolean getEncryptColumnEnabled() {
		return getBoolean("toilet.db.encrypt.column.enabled");
	}
	
	public String getEncryptKey() {
		return getString("toilet.db.encrypt.key");
	}
	
	public String getEncryptColumnLists() {
		return getString("toilet.db.encrypt.column.lists");
	}
	
	public String getDbChartSet() {
		return getString("toilet.db.charset");
	}
	
	/**
	 * <pre>
	 * 설정파일(.properties)에 설정한 key에 대한 value를 리턴한다. 
	 * </pre>
	 * @author HJKIM
	 */
	@Override
	public String getString(String key){
		/**
		 * Override한 이유는 프로퍼티 파일에서 key에 대한 value값이  다른 key을 참조하도록 하기위함이다 
		 * 아래 예시는 key "usimcert.read.script.path" 에대한 value값이 또다른 key를 참조하고 있다. 
		 * 예시) usimcert.read.script.path={admin.inner.file.base.dir}/script
		 * 기본적으로는 참조할 수 없기 때문에 참조가 가능하도록 하였다.
		 */
		String value = super.getString(key);
		if (!StringUtil.isNull(value)) {
			Pattern pattern = Pattern.compile("\\{[a-zA-Z0-9-/-_-.]*\\}");
			Matcher matcher = pattern.matcher(value);
			String findStr = "" ;
			while(matcher.find()){
				findStr = matcher.group();
				String removeBrace = findStr.replaceAll("[\\{|\\}]", "");
				String replaceBrace = this.getString(removeBrace);
				if (!StringUtil.isNull(replaceBrace)) {
					value =value.replaceAll("\\{"+removeBrace+"\\}", replaceBrace);
				}
			}
		}
		return value;
	}
}

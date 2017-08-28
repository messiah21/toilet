package com.raon.toilet.common.dao;

import javax.annotation.Resource;

import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CommonDao extends SqlSessionDaoSupport{
	
	protected transient Logger logger = LoggerFactory.getLogger(this.getClass());
	
	
	@Resource(name = "sqlSession")
	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		super.setSqlSessionTemplate(sqlSessionTemplate);
	}	
	
	
	

}

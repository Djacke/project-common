package com.project.common.spring;

import org.apache.log4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * Sring 获取bean的工具 以及spring ApplicationEvent监听者
 * 
 */
public class SpringUtil implements ApplicationContextAware{

	private static ApplicationContext applicationContext;

//	private static Logger logger = Logger.getLogger(SpringUtil.class);

	@Override
	public void setApplicationContext(ApplicationContext context)
			throws BeansException {
		applicationContext = context;
	}
	
	/**
	 * 获取sping管理的bean
	 * 
	 * @param beanId
	 * @return Object
	 */
	public static Object getBean(String beanId) {
		return applicationContext.getBean(beanId);
	}
	
	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}
	
	public static <T> T getBean(Class<T> clazz) {
		return applicationContext.getBean(clazz);
	}

}

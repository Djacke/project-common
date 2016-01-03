package com.project.common.listener;

import java.util.Enumeration;
import java.util.Properties;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.project.common.config.EnvPropertiesConfiger;

public class LenovoServletContextListener implements ServletContextListener {
	public void contextInitialized(ServletContextEvent event) {
		ServletContext servletContext=event.getServletContext();
		if(servletContext!=null){
			//读取env配置文件
			Properties properties = EnvPropertiesConfiger.getProperties();
			if ( null != properties) {
				Enumeration enu = properties.propertyNames();
				if ( null != enu ) {
					while(enu.hasMoreElements()){
						String key = (String)enu.nextElement();
						servletContext.setAttribute(key, properties.getProperty(key));
					}
				}
			}
			servletContext.setAttribute("jsPath", EnvPropertiesConfiger.getValue("jsPath"));
			servletContext.setAttribute("cssPath", EnvPropertiesConfiger.getValue("cssPath"));
			servletContext.setAttribute("imgPath", EnvPropertiesConfiger.getValue("imgPath"));
		}
	}

	public void contextDestroyed(ServletContextEvent sce) {
	}

}

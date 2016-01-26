package com.project.common.utils;

import java.io.IOException;
import java.text.SimpleDateFormat;

import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

public class JsonUtils {
	
	private static Logger logger = Logger.getLogger(JsonUtils.class);
	
	private static final ObjectMapper mapper = new ObjectMapper();

	static
	{
		mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
	}
	
	public static String object2Json(Object obj)
	{
		try {
			return mapper.writeValueAsString(obj);
		} catch (JsonGenerationException e) {
			logger.info(e.getMessage());
			e.printStackTrace();
		} catch (JsonMappingException e) {
			logger.info(e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			logger.info(e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
	
	public static <T> T json2Object(String json,Class<T> cls)
	{
		try {
			return mapper.readValue(json,cls);
		} catch (JsonParseException e) {
			logger.info(e.getMessage());
			e.printStackTrace();
		} catch (JsonMappingException e) {
			logger.info(e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			logger.info(e.getMessage());
			e.printStackTrace();
		}
		return null;
	}
}

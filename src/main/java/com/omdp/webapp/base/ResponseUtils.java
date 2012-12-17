package com.omdp.webapp.base;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;


public class ResponseUtils {

	public static void writeJSONString(HttpServletResponse response, Map<String, Object> map) throws IOException {
		JSONObject obj = new JSONObject();
		Iterator<String> itr = map.keySet().iterator();
		while(itr.hasNext()){
			String key = itr.next();
			obj.put(key, map.get(key));
		}
		ResponseUtils.writeString(response,obj.toString());
	}
	
	public static void writeString(HttpServletResponse response,String content) throws IOException{
		response.setHeader("Pragma", "No-cache");   
		response.setHeader("Cache-Control", "No-cache");   
		response.setDateHeader("Expires", 0);   
		response.setContentType("text/plain; charset=UTF-8");
		
		response.getWriter().write(content);
	}

}

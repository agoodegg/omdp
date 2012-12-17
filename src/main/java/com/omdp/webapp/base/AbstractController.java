package com.omdp.webapp.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.omdp.webapp.base.common.model.Page;
import com.omdp.webapp.base.common.web.SessionManager;
import com.omdp.webapp.model.TUser;

public class AbstractController extends SimpleFormController {


	public AbstractController()
    {
		
    }
	
	
    private int start;
    private int limit;
    public String errors;
    public String success;
    public String msg;
	
	public int getStart() {
		return start;
	}
	
	public void setStart(int start) {
		this.start = start;
	}
	
	public int getLimit() {
		return limit;
	}
	
	public void setLimit(int limit) {
		this.limit = limit;
	}
    
	
	public ModelAndView getModel(String name, Object obj)
    {
        Map modelMap = new HashMap(1);
        modelMap.put(name, obj);
        modelMap.put("success", Boolean.valueOf(true));
        return new ModelAndView("jsonView", modelMap);
    }

    public ModelAndView getModelPage(Page page)
    {
        Map modelMap = new HashMap(3);
        modelMap.put("total", Long.valueOf(page.getTotalCount()));
        modelMap.put("data", page.getResult());
        modelMap.put("success", Boolean.valueOf(true));
        return new ModelAndView("jsonView", modelMap);
    }

    public ModelAndView getModelMapError(String msg)
    {
        Map modelMap = new HashMap(2);
        if(msg == null || msg.equals(""))
            msg = "\u64CD\u4F5C\u5931\u8D25";
        modelMap.put("message", msg);
        modelMap.put("failure", Boolean.valueOf(false));
        return new ModelAndView("jsonView", modelMap);
    }

    public ModelAndView getModelMapSuccess(String msg)
    {
        Map modelMap = new HashMap(2);
        if(msg == null || msg.equals(""))
            msg = "\u64CD\u4F5C\u6210\u529F";
        modelMap.put("message", msg);
        modelMap.put("success", Boolean.valueOf(true));
        return new ModelAndView("jsonView", modelMap);
    }

    public TUser getCurtUser(HttpServletRequest request)
    {
        SessionManager sm = new SessionManager(request);
        TUser user = (TUser)sm.getValue("user");
        return user;
    }
    

	public static void exportFile(HttpServletResponse response, File file, String fileName , boolean isDel) throws IOException {
		OutputStream out = null;
		InputStream in = null;

		// 获得文件名
		String filename = URLEncoder.encode(fileName, "UTF-8");
		// 定义输出类型(下载)
		response.setContentType("application/force-download");
		response.setHeader("Location", filename);
		// 定义输出文件头
		response.setHeader("Content-Disposition", "attachment;filename=" + filename);
		out = response.getOutputStream();
		in = new FileInputStream(file.getPath());

		byte[] buffer = new byte[1024];
		int i = -1;
		while ((i = in.read(buffer)) != -1) {
			out.write(buffer, 0, i);
		}

		in.close();
		out.close();

		if (isDel) {
			// 删除文件,删除前关闭所有的Stream.
			file.delete();
		}
	}
}

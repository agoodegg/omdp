package com.omdp.webapp.base.taglib;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyContent;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.tagext.Tag;


/**
 * matrix for the opr
 * <pre>
 * 
 * 00000                 打单    修改    完工    作废    /     /  
 * 00001                 /     /      /      /      /     /    异常工单
 * 00010                 打单     /      /      /      /     核销  异常工单
 * 00011                 /     /      /      /      /     /    异常工单
 * 00100                  /      /      /      /      /     /
 * 00101                  /      /      /      /      /     /    异常工单
 * 00110                  /      /      /      /      /     /    异常工单
 * 00111                  /      /      /      /      /     /    异常工单
 * 01000                  打单   修改     /     作废    结算   /
 * 01001                  /     /      /      /      /     /
 * 01010                  打单     /      /      /      /     核销
 * 01011                  /     /      /      /      /     /
 * 01100                  /      /      /      /      /     /
 * 01101                  /      /      /      /      /     /
 * 01110                  /      /      /      /      /     /
 * 01111                  /      /      /      /      /     /
 * 10000                 打单    修改    完工    作废    /     /  
 * 10001                 /     /      /      /      /     /    异常工单
 * 10010                 打单     /      /      /      /     核销  异常工单
 * 10011                 /     /      /      /      /     /    异常工单
 * 10100                  /      /      /      /      /     /
 * 10101                  /      /      /      /      /     /    异常工单
 * 10110                  /      /      /      /      /     /    异常工单
 * 10111                  /      /      /      /      /     /    异常工单
 * 11000                  打单   修改     /     作废    结算   /
 * 11001                  /     /      /      /      /     /
 * 11010                  打单     /      /      /      /     核销
 * 11011                  /     /      /      /      /     /
 * 11100                  /      /      /      /      /     /
 * 11101                  /      /      /      /      /     /
 * 11110                  /      /      /      /      /     /
 * 11111                  /      /      /      /      /     /
 * </pre>
 */
public class OrderStatusOpr extends BodyTagSupport {

	public PageContext pageContext;
	public Tag parent;

	private static Map<String, String> oprMatrix;
	
	public static final int PRINT = 32;
	public static final int EDIT  =  16;
	public static final int DONE  =  8;
	public static final int TRASH =  4;
	public static final int PAY   =  2;
	public static final int CHECK =  1;

	private String status;

	private String opr;

	static {
		oprMatrix = new HashMap<String, String>();
		oprMatrix.put("00000", "111100");
		oprMatrix.put("00001", "000000");
		oprMatrix.put("00010", "100001");
		oprMatrix.put("00011", "000000");
		oprMatrix.put("00100", "000000");
		oprMatrix.put("00101", "000000");
		oprMatrix.put("00110", "000000");
		oprMatrix.put("00111", "000000");
		oprMatrix.put("01000", "110110");
		oprMatrix.put("01001", "000000");
		oprMatrix.put("01010", "100001");
		oprMatrix.put("01011", "000000");
		oprMatrix.put("01100", "000000");
		oprMatrix.put("01101", "000000");
		oprMatrix.put("01110", "000000");
		oprMatrix.put("01111", "000000");
		oprMatrix.put("10000", "111100");
		oprMatrix.put("10001", "000000");
		oprMatrix.put("10010", "100001");
		oprMatrix.put("10011", "000000");
		oprMatrix.put("10100", "000000");
		oprMatrix.put("10101", "000000");
		oprMatrix.put("10110", "000000");
		oprMatrix.put("10111", "000000");
		oprMatrix.put("11000", "110110");
		oprMatrix.put("11001", "000000");
		oprMatrix.put("11010", "100001");
		oprMatrix.put("11011", "000000");
		oprMatrix.put("11100", "000000");
		oprMatrix.put("11101", "000000");
		oprMatrix.put("11110", "000000");
		oprMatrix.put("11111", "000000");
	}


	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getOpr() {
		return opr;
	}

	public void setOpr(String opr) {
		this.opr = opr;
	}

	public int doStartTag() throws JspException {
		String oprSec = oprMatrix.get(status);
		
		if(oprSec==null){
			return SKIP_BODY;
		}
		
		Integer fopr = Integer.parseInt(oprSec,2);
		
		Integer oprInt = Integer.parseInt(opr,2);
		
		if((fopr&oprInt) == oprInt){
			return EVAL_BODY_BUFFERED;
		}
		else{
			return SKIP_BODY;
		}
	}

	public int doAfterBody() throws JspException {
		BodyContent body = getBodyContent();
		String code = body.getString();
		JspWriter out = body.getEnclosingWriter();
		try {
			out.print(code);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return SKIP_BODY;
	}
}

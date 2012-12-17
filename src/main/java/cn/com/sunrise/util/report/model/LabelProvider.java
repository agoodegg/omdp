package cn.com.sunrise.util.report.model;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 用于向表格提供单元格的数据表示
 * @author hegang
 *
 */
public class LabelProvider {
	
	private static LabelProvider defaultLabelProvider=new LabelProvider();
	
	/**
	 * 获得日期的LabelProvider
	 * @param pattern
	 * @return
	 */
	public static LabelProvider getDateLabelProvider(String pattern){
		final SimpleDateFormat sdf=new SimpleDateFormat(pattern);
		return new LabelProvider(){
			public String getLabel(Object data) {
				if(data==null)return "";
				return sdf.format((Date)data);
			}
		};
	}
	
	/**
	 * 获得数字的LabelProvider
	 * @param pattern
	 * @return
	 */
	public static LabelProvider getNumberLabelProvider(String pattern){
		final DecimalFormat df = new DecimalFormat(pattern);
		return new LabelProvider(){
			public String getLabel(Object data) {
				if(data==null)data=new Double(0);
				return df.format((Number)data);
			}			
		};
	}
	
	public static LabelProvider getDefaultValueProvider(String value){
		final String v = value;
		return new LabelProvider(){
			public String getLabel(Object data){
				return data == null?v:data.toString();
			}
		};
	}
	
	
	public static LabelProvider getDefaultLabelProvider(){
		return defaultLabelProvider;
	}
	
	/**
	 * 获得单元格的数据表示
	 * @param data
	 * @return
	 */
	public String getLabel(Object data){
		return data==null ? "" : data.toString();
	}

	/**
	 * 获得单元格的样式
	 * @param data
	 * @return
	 */
	public String getStyleClass(Object data) {
		return null;
	}



	
	
}

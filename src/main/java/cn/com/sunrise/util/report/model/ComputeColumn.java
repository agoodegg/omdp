package cn.com.sunrise.util.report.model;

import java.util.HashMap;

/**
 * 计算列
 * @author hegang
 *
 */
public class ComputeColumn extends Column{

	String expression;
	HashMap relativeColumnKeys=new HashMap();
	
	public ComputeColumn(Column parent) {
		super(parent);
		this.setKey(getReport().getDataTable().createComputeColumnKey());
	}

	public ComputeColumn(Report report) {
		super(report);
	}

	/**
	 * 添加相关列
	 * @param column
	 */
	public void addRelativeColumn(Column column){
		relativeColumnKeys.put(column.getTitle(),column.getKey());
	}
	
	/**
	 * 添加相关列键
	 * @param name
	 * @param colKey
	 */
	public void addRelativeColumnKey(String name,Object colKey){
		relativeColumnKeys.put(name, colKey);
	}

	
	public String getExpression() {
		return expression;
	}

	public void setExpression(String expression) {
		this.expression = expression;
	}
	

	public Object getData(Object rowKey){
		DataTable table=getReport().getDataTable();
		if(!table.getColumnKeys().contains(this.getKey())){
			table.setComputeColumn(getKey(), relativeColumnKeys, expression);
		}
		return table.getData(rowKey, this.getKey());
	}
	

	
	
	
}

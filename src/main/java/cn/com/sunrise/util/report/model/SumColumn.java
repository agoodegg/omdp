package cn.com.sunrise.util.report.model;

import java.util.LinkedList;

/**
 * 汇兑列
 * 用于表的横行累计
 * @author hegang
 *
 */
public class SumColumn extends Column {

	LinkedList relativeColumnKeys=new LinkedList();
	
	public SumColumn(Column parent) {
		super(parent);
		this.setKey(getReport().getDataTable().createComputeColumnKey());
	}

	public SumColumn(Report report) {
		super(report);
	}

	public void addRelativeColumn(Column column){
		relativeColumnKeys.add(column.getKey());
	}
	
	public void addRelativeColumnKey(Object columnKey){
		relativeColumnKeys.add(columnKey);
	}
	
	public Object getData(Object rowKey){
		DataTable table=getReport().getDataTable();
		if(!table.getColumnKeys().contains(this.getKey())){
			table.setSumColumn(getKey(),relativeColumnKeys);
		}
		return table.getData(rowKey, this.getKey());
	}
	
}

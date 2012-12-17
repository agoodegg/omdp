package cn.com.sunrise.util.report.model;

import java.util.ArrayList;
import java.util.Collection;


/**
 * 
 * @author longhai
 *
 */
public class DataTableSortable extends DataTable{

	
	private Collection<Object> rowKeyList = new ArrayList<Object>();

	/*
	 * (non-Javadoc)
	 * @see cn.com.sunrise.zsintegral.tools.report.model.DataTable#setData(java.lang.Object, java.lang.Object, java.lang.Object)
	 */
	public void setData(Object rowKey,Object columnKey,Object value){
		super.setData(rowKey, columnKey, value);
		if(!rowKeyList.contains(rowKey)){
			rowKeyList.add(rowKey);
		}
	}

	/*
	 * (non-Javadoc)
	 * @see cn.com.sunrise.zsintegral.tools.report.model.DataTable#getRowKeys()
	 */
	public Collection getRowKeys(){
		return rowKeyList;
	}
	

	
}

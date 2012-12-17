package cn.com.sunrise.util.report.model;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import java.util.Map.Entry;

import cn.com.sunrise.util.Evaluator;

/**
 * 数据表
 * 用于存放二维数据
 * @author hegang
 *
 */
public class DataTable {

	
	TreeMap data=new TreeMap();
	int computeColumnCount=0;
	HashMap computeColumns=new HashMap();
	HashSet columnKeys=new HashSet();
	
	
	/**
	 * 获得单元格的值
	 * @param rowKey
	 * @param columnKey
	 * @return
	 */
	public Object getData(Object rowKey,Object columnKey){
		ComputableColumn computeColumn=(ComputableColumn) computeColumns.get(columnKey);
		if(computeColumn!=null){
			return new Double(computeColumn.compute(rowKey));
		}
		Map columnMap=(Map) data.get(rowKey);
		if(columnMap==null)return null;
		return columnMap.get(columnKey);
	}
	
	/**
	 * 设置单元格的值
	 * @param rowKey
	 * @param columnKey
	 * @param value
	 */
	public void setData(Object rowKey,Object columnKey,Object value){
		columnKeys.add(columnKey);
		Map columnMap=(Map)data.get(rowKey);
		if(columnMap==null){
			columnMap=new HashMap();
			data.put(rowKey, columnMap);
		}
		columnMap.put(columnKey, value);
	}
	
	/**
	 * 获得所有行的键
	 * @return
	 */
	public Collection getRowKeys(){
		return data.keySet();
	}
	
	/**
	 * 是否有指定行
	 * @param rowKey
	 * @return
	 */
	public boolean containsRow(Object rowKey){
		return data.keySet().contains(rowKey);
	}
	
	/**
	 * 设置计算列
	 * @param colKey 计算列的列键
	 * @param relativeColumnKeys 相关的列键,键名=>键
	 * @param expression 数学表达式
	 * @return
	 */
	public void setComputeColumn(Object colKey,final Map relativeColumnKeys,final String expression){
		columnKeys.add(colKey);
		computeColumns.put(colKey, new ComputableColumn(){			
			public double compute(Object rowKey) {
				HashMap params=new HashMap();
				for(Iterator itr=relativeColumnKeys.entrySet().iterator();itr.hasNext();){
					Map.Entry entry=(Entry) itr.next();
					Object value=getData(rowKey, entry.getValue());
					params.put(entry.getKey(),value);
				}
				return eval(expression, params);
			}			
		});
	}
	
	/**
	 * 创建自动生成的列键
	 * @return
	 */
	public String createComputeColumnKey(){
		return "_compute_column_" + computeColumnCount++;
	}
	
	/**
	 * 设置合计列
	 * @param colKey
	 * @param relativeColumnKeys
	 */
	public void setSumColumn(Object colKey,Collection relativeColumnKeys){
		String exp="";
		int count=0;
		HashMap map=new HashMap();
		for(Iterator itr=relativeColumnKeys.iterator();itr.hasNext();){
			String name="column" + count++;
			map.put(name,itr.next());
			exp += name + (itr.hasNext() ? " + " : "");
		}
		setComputeColumn(colKey,map,exp);		
	}
	
	
	double eval(String exp,Map param){
		double result=Evaluator.eval(exp,param);
		return Double.isInfinite(result) || Double.isNaN(result) ? 0 : result;
	}
	
	

	private interface ComputableColumn{
		double compute(Object rowKey);
	}


	public HashSet getColumnKeys() {
		return columnKeys;
	}
	

	
}

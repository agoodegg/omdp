package cn.com.sunrise.util.report.model;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.com.sunrise.util.Evaluator;

/**
 * 表脚计算列
 * 
 * 可通过定义计算公式来定义表脚的计算列<p>
 * 如净增列的表脚计算公式为:<p>
 * sum(新增)-sum(退网)<p>
 * 目前支持的聚集函数有sum(列键名),avg(列键名),count()<p>
 * 
 * 定义表脚计算列应同时使用addRelativeColumnKey(列键名,列键)定义相关列<p>
 * 也可使用addRelativeColumn(Column)来定义,这时的列键名为列的标题,列键为列的键
 * 
 * @author hegang
 *
 */
public class FootComputeColumn extends FootColumn{

	HashMap relativeColumnKeys = new HashMap();
	String expression = "";

	public FootComputeColumn(Foot foot, Column positionColumn) {
		super(foot, positionColumn);
	}

	public String getExpression() {
		return expression;
	}

	public void setExpression(String expression) {
		this.expression = expression;
	}

	public void addRelativeColumn(Column column) {
		this.relativeColumnKeys.put(column.getTitle(), column.getKey());
	}

	public void addRelativeColumnKey(String name, Object columnKey) {
		this.relativeColumnKeys.put(name, columnKey);
	}

	/**
	 * 列汇总
	 * @param exp
	 * @return
	 */
	private double computeSum(String exp,GroupCell gc) {
		double sum=0;
		for(Iterator itr=gc.getRowKeys().iterator();itr.hasNext();){			
			sum += eval(exp, getParams(itr.next()));
		}
		return sum;
	}

	/**
	 * 列平均值
	 * @param exp
	 * @return
	 */
	private double computeAvg(String exp,GroupCell gc) {
		double sum=0;
		int count=0;
		for(Iterator itr=gc.getRowKeys().iterator();itr.hasNext();){
			sum += eval(exp, getParams(itr.next()));
			count++;
		}
		return count==0 ? 0 : sum/count;
	}

	private double computeCount(GroupCell gc) {
		return gc.getRowKeys().size();
	}

	private Map getParams(Object rowKey) {
		DataTable table=this.getFoot().getReport().getDataTable();
		HashMap map=new HashMap();
		for(Iterator itr=relativeColumnKeys.entrySet().iterator();itr.hasNext();){
			Map.Entry entry=(Entry) itr.next();
			map.put(entry.getKey(),table.getData(rowKey,entry.getValue()));
		}
		return map;
	}

	public Object getData(GroupCell gc) {
		String exp=expression.toUpperCase();
		
		StringBuffer buf=new StringBuffer();
		Pattern pattern=Pattern.compile("SUM\\((.+?)\\)|AVG\\((.+?)\\)|COUNT\\((\\*)\\)");
		Matcher m=pattern.matcher(exp);
		HashMap params=new HashMap();
		int paramCount=0;
		while(m.find()){
			String sumExp=m.group(1);
			String avgExp=m.group(2);
			//String countExp=m.group(3);
			String value;
			if(sumExp!=null){
				value=String.valueOf(computeSum(sumExp,gc));
			}else if(avgExp!=null){
				value=String.valueOf(computeAvg(avgExp,gc));
			}else{
				value=String.valueOf(computeCount(gc));
			}
			String name="p" + paramCount++;
			params.put(name, value);
			m.appendReplacement(buf,name);
		}	
		
		double result=eval(buf.toString(),params);
		return new Double(result);		
	}

	double eval(String exp, Map param) {
		double result=Evaluator.eval(exp,param);
		return Double.isInfinite(result) || Double.isNaN(result) ? 0 : result;
	}
	
}

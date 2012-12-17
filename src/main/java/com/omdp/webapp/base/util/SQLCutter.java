package com.omdp.webapp.base.util;

import java.io.StringReader;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Stack;

import org.apache.commons.beanutils.BeanUtils;


/**
 * SQL剪辑器<p>
 * 
 * 功能:<p>
 * 
 * 根据查询参数对SQL进行剪辑<p>
 * 
 * 例如:<p>
 * <b>SELECT * FROM Persons WHERE FirstName LIKE ? </b><p>
 * 如果将第一个参数设为空,将返回<p>
 * <b>SELECT * FROM Persons</b><p>
 * 
 * 该功能分三步实现:<p>
 * 1.将SQL语句解析为AST(Abstract Syntax Tree)<p>
 * 2.通过对AST分析,删除空参数节点及与其相关的其它节点<p>
 * 3.根据AST生成SQL语句<p>
 * 
 * @author gary he<p>
 * 
 * modify by kevin.zhou<p>
 * 增加根据标记剪切子表达式功能<p>
 * 
 * 例如:<p>
 * <b>SELECT * FROM Persons S WHERE S.id IN (SELECT * FROM TempTable T WHERE T.someField like ?)</b><p>
 * 未修改前空参数剪切结果为:<p>
 * <b>SELECT * FROM Persons S WHERE S.id IN (SELECT * FROM TempTable T )</b><p>
 * 如希望剪切为如下形式：<p>
 * <b>SELECT * FROM Persons S</b><p>
 * 
 * 则:<p>
 * <b>SELECT * FROM Persons S WHERE S.id IN {(SELECT * FROM TempTable T WHERE T.someField like ?)}</b><p>
 * 
 * 并在剪切前调用preProcess(String sql,Collection params)进行预剪切,最后再调用cut方法得到剪切结果<p>
 * 
 * 
 *
 */
public class SQLCutter {
	

	/**
	 * 动态调用get方法
	 * 
	 * @param object
	 * 			用于得到属性值的实体对象,一般为FormBean
	 * @param fieldName
	 * 			属性名
	 * @param args
	 * 			调用对于getXXX方法的参数组
	 * 
	 * @return Object
	 * 			返回Object对象,字符串类型居多
	 * 
	 */
	public static Object call(Object object, String fieldName, Object[] args) {
		if(object==null){
			return null;
		}
		if(object instanceof Map){
			Map m = (Map)object;
			String[] s = (String[])(m.get(fieldName));
			if(s==null||s.length==0){
				return null;
			}
			else{
				StringBuffer buf = new StringBuffer();
				for(String si:s){
					buf.append(si).append(",");
				}
				return buf.subSequence(0,buf.length()-1);
			}
		}
		else{
			String methodName = "get" + fieldName.substring(0, 1).toUpperCase()
				+ fieldName.substring(1);
			Object obj = null;
			try {
				Method m = object.getClass().getMethod(methodName, new Class[] {});
				obj = m.invoke(object, args);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return obj;
		}
	}
	
	/**
	 * 构造参数数组  用以sql的剪辑
	 * 
	 * @param sqlInfo
	 * 				SqlInfo对象
	 * @param form
	 * 				FormBean,提供的查询参数实体
	 * 
	 * @return Collection
	 * 				将查询参数构造为参数组
	 */
//	public static Collection buildParams(SqlInfo sqlInfo, Object form) {
//		List<Parameter> params = sqlInfo.getParams();
//		List<String> paramData = new ArrayList<String>();
//		
//		Map<String,String> paramsMap = new HashMap<String,String>();
//		if(params!=null){                                                      //非命名参数  ?占位符  注意所配置的parameter与sql中的?占位符顺序匹配
//			for(Parameter p:params){
//				String s = null;
//				try{
//					Object o = (Object)(SQLCutter.call(form, p.getName(), null));
//					if(o!=null){
//						s = String.valueOf(o);
//					}
//				}
//				catch(Exception e){
//					e.printStackTrace();
//				}
//				p.setVal(s);
//				paramsMap.put(p.getName(), p.toString());
//				paramData.add(p.toString());
//			}
//		}
//		if(sqlInfo.getNameParamter()==null||sqlInfo.getNameParamter().size()==0){   //命名参数
//			return paramData;
//		}
//		else{
//			paramData = new ArrayList<String>();
//			for(String nameParam:sqlInfo.getNameParamter()){
//				paramData.add(paramsMap.get(nameParam));
//			}
//			
//			return paramData;
//		}
//		
//	}
	
	/**
	 * 预处理标记过的SQL   用于剪切子表达式
	 * 
	 * @param sql
	 * 			raw sql,待处理的sql
	 * @param params
	 * 			参数组,根据提供的参数组对sql进行预剪辑
	 * 
	 * @return String
	 * 			预剪辑的sql,剪辑标记为[为空删除]的子表达式
	 */
	public static String preProcess(String sql,Collection params){
		return preProcess(sql,(String[])params.toArray(new String[params.size()]));
	}
	
	/**
	 * 预处理标记过的SQL   用于剪切子表达式
	 * 
	 * @param sql
	 * 			raw sql,待处理的sql
	 * @param params
	 * 			参数组,根据提供的参数组对sql进行预剪辑
	 * 
	 * @return String
	 * 			预剪辑的sql,剪辑标记为[为空删除]的子表达式
	 */
	public static String preProcess(String sql,String[] data){
		//boolean strEscapeMode = false;
		Stack stack=new Stack();            //临时栈  用于匹配标记的大括号元组
		ArrayList<PosTuple> positions = new ArrayList<PosTuple>();
		ArrayList<Integer> paramsPos = new ArrayList<Integer>();
		boolean markEscapeMode = false;
		StringBuffer tempSql = new StringBuffer();
		int ipos=0;
		for(int i=0;i<sql.length();i++){
			char c = sql.charAt(i);
			if(c=='\\'){                         //置为转义模式
				markEscapeMode = !markEscapeMode;
				continue;
			}
			if('?'==c&&!markEscapeMode){
				paramsPos.add(i);
				if(data[ipos]!=null){
					tempSql.append('\02');
				}
				else{
					tempSql.append('?');
				}
				ipos++;
			}
			
			else if('{'==c&&!markEscapeMode){
				stack.push(i);
				tempSql.append(' ');
			}
			else if('}'==c&&!markEscapeMode){
				int sPos = ((Integer)(stack.pop())).intValue();
				int count = searchFn(paramsPos,sPos,i);
				PosTuple posTuple = new PosTuple(sPos,i,count);
				positions.add(posTuple);
				tempSql.append(' ');
			}
			else{
				tempSql.append(c);
			}
			markEscapeMode = false;              //重置转义标识
		}
		
		replaceSubExp(tempSql,positions.toArray(new PosTuple[positions.size()]));
		return tempSql.toString().replaceAll("\02", "?").replaceAll("\01+", " ");
	}
	
	/**
	 * 查找该子表达式里参数个数
	 * 
	 * @param paramsPos     所有参数对应当索引位置
	 * @param sPos          子表达式开始索引
	 * @param sEnd          子表达式结束索引
	 * 
	 * @return int
	 * 				        参数个数
	 */
	private static int searchFn(ArrayList<Integer> paramsPos,int sPos,int sEnd){
		int icount=0;
		for(Integer i:paramsPos){
			if(i>sPos&&i<sEnd){
				icount++;
			}
		}
		return icount;
	}
	
	/**
	 * 用于解析和剪切标记过替换为空的空参数字表达式
	 * 
	 * @param sqlbuf 
	 * 					the sql has replaced by params (not null params were replaced with '\02')
	 */
	private static void replaceSubExp(StringBuffer sqlbuf,PosTuple[] positionData){
		int deep=0;
		int iStartPos = 0;
		int iEndPos = 0;
		
		for(PosTuple p:positionData){
			int foundIndex = sqlbuf.indexOf("\02", p.start);
			if(foundIndex!=-1&&foundIndex<=p.end){
				
			}
			else{
				int len = p.end-p.start;
				StringBuffer tempbuf = new StringBuffer();
				for(int e=0;e<len;e++){
					if(e<p.countParams){
						tempbuf.append("?");
					}
					else{
						tempbuf.append("\01");
					}
				}
				sqlbuf.replace(p.start, p.end, tempbuf.toString());
			}
		}
	}
	
	
	
	public static String cutToCountSQL(String sql,Collection params){
		return cutToCountSQL(sql,(String[])params.toArray(new String[params.size()]));
	}
	
	public static String cutToCountSQL(String sql,String[] params){
		if(sql==null)return sql;
		SQLParser parser=new SQLParser(sql,params);
		ListExp exp=parser.parse();
		cut(exp);
		toCountSQL(exp);
		StringBuffer buf=new StringBuffer();
		exp.toSQL(buf);
		return buf.toString();
	}
	
	public static String cutToCountSQL(StringBuffer sqlbuf,String[] params){
		return cutToCountSQL(sqlbuf.toString(),params);
	}
	

	private static void toCountSQL(ListExp listExp) {
		for(Iterator itr=listExp.elements.iterator();itr.hasNext();){
			Exp exp=(Exp) itr.next();
			if(exp.isSelectExp()){
				SelectExp selectExp=(SelectExp) exp;
				selectExp.elements.clear();
				LiteralExp countLiteral=new LiteralExp("count");
				BracketExp bracketExp=new BracketExp();
				bracketExp.elements.add(new LiteralExp("*"));
				selectExp.elements.add(countLiteral);
				selectExp.elements.add(bracketExp);
				break;
			}
		}
		
	}




	/**
	 * 
	 * @param sql SQL语句
	 * @param params 参数集合
	 * @return
	 */
	public static String cut(String sql,Collection params){
		if(params==null)return sql;
		return cut(sql,(String[])params.toArray(new String[params.size()]));
	}
	
	/**
	 * 剪辑SQL
	 * @param sql SQL语句
	 * @param params 参数数组
	 * @return
	 */
	public static String cut(String sql,String[] params){
		if(sql==null || params==null || params.length==0)return sql;
		SQLParser parser=new SQLParser(sql,params);
		ListExp exp=parser.parse();
		cut(exp);
		StringBuffer buf=new StringBuffer();
		exp.toSQL(buf);
		return buf.toString();
	}
	
	/**
	 * 剪辑SQL
	 * @param sql SQL语句
	 * @param params 参数数组
	 * @return
	 */
	public static String cut(StringBuffer sqlbuf,String[] params){
		return cut(sqlbuf.toString(),params);
	}
	
	
	/**
	 * 剪辑列表表达式
	 * 该函数为剪辑算法的核心
	 * @param listExp
	 */
	static void cut(ListExp listExp){
		List result=new LinkedList();
		for(Iterator itr=listExp.elements.iterator();itr.hasNext();){
			Exp exp=(Exp) itr.next();
			if(exp.isListExp()){
				cut((ListExp) exp);
			}
			if(exp.isLogicExp()){
				if(hasCutable(result))removeCutable(result);
				removeLastLogicExp(result);
				if(!result.isEmpty())result.add(exp);
			}else{
				result.add(exp);
			}
		}
		if(hasCutable(result))removeCutable(result);	
		removeLastLogicExp(result);
		listExp.elements=result;
	}
	
	static boolean hasCutable(List result){
		ListIterator itr=result.listIterator(result.size());
		while(itr.hasPrevious()){
			Exp exp=(Exp) itr.previous();
			if(exp.isLogicExp())return false;
			if(exp.cutable())return true;
		}
		return false;
	}
	
	static void removeCutable(List result){
		ListIterator itr=result.listIterator(result.size());
		while(itr.hasPrevious()){
			Exp exp=(Exp) itr.previous();
			if(exp.isLogicExp())return;
			itr.remove();
		}
	}

	static void removeLastLogicExp(List result) {
		Exp lastExp=(Exp) (result.size()==0 ? null : result.get(result.size() - 1));
		if(lastExp!=null && lastExp.isLogicExp()){
			result.remove(result.size() - 1);
		}
	}
	
	/**
	 * SQL解析器
	 *
	 */
	static class SQLParser{
		static final int EOF=-1;
		static final int WHERE=0;
		static final int GROUP=1;
		static final int ORDER=2;
		static final int BRACKET_START=3;
		static final int BRACKET_END=4;
		static final int PARAMETER=5;
		static final int AND=6;
		static final int OR=7;
		static final int SELECT=8;
		static final int FROM=9;
		
		static String[] keywords=new String[10];
		
		
		static{
			keywords[OR]=" OR ";
			keywords[WHERE]=" WHERE ";
			keywords[GROUP]=" GROUP ";
			keywords[ORDER]=" ORDER ";
			keywords[BRACKET_START]="(";
			keywords[BRACKET_END]=")";
			keywords[PARAMETER]="?";
			keywords[AND]=" AND ";	
			keywords[SELECT]="SELECT ";	
			keywords[FROM]=" FROM ";
		}
		
		String sql;		
		CharBuffer charBuffer=new CharBuffer();
		Stack stack=new Stack();
		
		String[] params;
		int paramPointer=0;	
		
		SQLParser(String sql,String[] params){
			this.sql=sql;
			this.params=params;
		}


		public ListExp parse(){
			try{
				return doParse();
			}catch(Exception ex){
				throw new RuntimeException("SQL语法错误:\n" + sql,ex);
			}
		}

		public ListExp doParse() throws Exception{
			StringReader reader=new StringReader(sql);
			stack.push(new ListExp());
			int c=reader.read();
			while(c>=0){
				if(c=='')c=' ';
				if(isWordChar(c)){
					charBuffer.put((char)c);
				}else{					
					if(isWordChar(lastChar())){
						matchAndDispatch();
					}
					charBuffer.put((char)c);
					if(c=='\n'){
						charBuffer.put(' ');
					}
					matchAndDispatch();
				}				
				c=reader.read();
			}
			dispatch(EOF,null);
			if(stack.size()!=1)throw new Exception();
			return (ListExp)stack.pop();
		}


		private short lastChar() {
			return (short) charBuffer.lastChar();
		}
		
		private boolean isWordChar(int c) {
			return c>='a' && c<='z' || c>='A' && c<='Z';
		}


		private void matchAndDispatch() {
			for(int i=0;i<keywords.length;i++){
				if(charBuffer.matchIgnoreCase(keywords[i])){
					String literal=charBuffer.clear(keywords[i].length());
					dispatch(i,literal);
					return;
				}
			}
		}


		void dispatch(int keyword,String literal){
			switch(keyword){
			case SELECT :
				endLiteral();
				SelectExp selectExp=new SelectExp();
				selectExp.literal=literal;
				stack.push(selectExp);
				break;
			case FROM :
				endLiteral();
				pullUp();
				FromExp fromExp=new FromExp();
				fromExp.literal=literal;
				stack.push(fromExp);
				break;
			case WHERE :
				endLiteral();
				WhereExp whereExp=new WhereExp();
				whereExp.literal=literal;
				stack.push(whereExp);
				break;
			case GROUP :
			case ORDER :
				endWhere();
				charBuffer.put(literal);
				break;
			case BRACKET_START :
				endLiteral();
				BracketExp bracketExp=new BracketExp();
				stack.push(bracketExp);
				//插入空表达式,以避免没有参数的函数被剪切
				stack.push(new NothingExp());
				pullUp();
				break;
			case BRACKET_END :
				endBracket();
				break;
			case PARAMETER :
				endLiteral();
				if(paramPointer==params.length)throw new RuntimeException("没有足够的参数");
				ParameterExp paramExp=new ParameterExp();
				paramExp.value=params[paramPointer++];
				stack.push(paramExp);
				pullUp();
				break;
			case AND :
			case OR :
				endLiteral();
				LogicExp logicExp=new LogicExp();
				logicExp.literal=literal;
				stack.push(logicExp);
				pullUp();
				break;
			case EOF :
				endLiteral();
				pullUpAll();
				break;
			default :				
			}
		}
		
		private void endWhere() {
			endLiteral();
			Exp exp=(Exp) stack.peek();
			if(exp.isWhereExp()){
				pullUp();
			}			
		}

		private void endBracket() {
			endLiteral();
			Exp exp=(Exp) stack.peek();
			while(!exp.isBracketExp()){
				pullUp();
				exp=(Exp) stack.peek();
			}
			pullUp();
			
		}

		private void pullUp(){
			Exp exp=(Exp) stack.pop();
			ListExp listExp=(ListExp) stack.peek();
			listExp.elements.add(exp);			
		}
		
		private void pullUpAll(){
			while(stack.size()>1){
				pullUp();
			}
		}

		private void endLiteral() {
			String literal=charBuffer.clear();
			if("".equals(literal))return;
			LiteralExp literalExp=new LiteralExp(literal);		
			stack.push(literalExp);
			pullUp();
		}

	}
	
	
	/**
	 * 
	 * SQL语法的AST节点基类
	 *
	 */
	static abstract class Exp{

		public boolean isListExp() {
			return false;
		}

		public boolean isBracketExp() {
			return false;
		}

		public boolean isWhereExp() {
			return false;
		}

		public boolean isLogicExp() {
			return false;
		}
		
		public boolean isSelectExp(){
			return false;
		}
		public boolean isParameterExp(){
			return false;
		}
		
		/**
		 * 子类实现该方法指明该表达式是否可以被剪切
		 * @return
		 */
		public boolean cutable() {
			return false;
		}
		
		/**
		 * 用于生成该表达式的SQL
		 * @param buffer
		 */
		public abstract void toSQL(StringBuffer buffer);
		
		/**
		 * 主要用于在Debug时显示该表达式
		 */
		public String toString() {
			StringBuffer buf=new StringBuffer();
			toSQL(buf);
			return buf.toString();
		}
		
	}
	
	/**
	 * 参数表达式
	 *
	 */
	static class ParameterExp extends Exp{
		String value;
		public void toSQL(StringBuffer buffer) {
			buffer.append(value);
		}

		public boolean cutable() {
			return value==null || "".equals(value.trim());
		}

		public boolean isParameterExp() {
			return true;
		}
		
	}
	
	/**
	 * 字面表达式
	 * 用于保存与关键字无关的字符串
	 *
	 */
	static class LiteralExp extends Exp{
		String literal;
		
		public LiteralExp(String literal) {
			this.literal=literal;
		}
		
		public LiteralExp(){}

		public void toSQL(StringBuffer buffer) {
			buffer.append(literal);
		}
		
		public boolean isLiteralExp() {
			return true;
		}
		
	}
	
	/**
	 * 空表达式
	 *
	 */
	static class NothingExp extends Exp{

		public void toSQL(StringBuffer buffer) {
		}

		public boolean cutable() {
			return false;
		}

	}
	
	/**
	 * 
	 * 列表表达式,用于包含子表达式
	 *
	 */
	static class ListExp extends Exp{
		List elements=new ArrayList();
		
		public boolean isListExp() {
			return true;
		}

		public void toSQL(StringBuffer buffer) {
			for(Iterator itr=elements.iterator();itr.hasNext();){
				Exp exp=(Exp) itr.next();
				exp.toSQL(buffer);
			}
		}

		public boolean cutable() {
			return elements.isEmpty();
		}
	}
	
	/**
	 * 括号表达式
	 *
	 */
	static class BracketExp extends ListExp{

		public void toSQL(StringBuffer buffer) {
			buffer.append('(');
			super.toSQL(buffer);
			buffer.append(')');
		}

		public boolean isBracketExp() {
			return true;
		}		
	}
	
	/**
	 * 标记子表达式   用于剪切子表达式
	 * 
	 * @author kevin.zhou
	 *
	 */
	static class BigBracketExp extends ListExp{

		public void toSQL(StringBuffer buffer) {
			buffer.append('{');
			super.toSQL(buffer);
			buffer.append('}');
		}

		public boolean isBigBracketExp() {
			return true;
		}		
	}

	static class LiteralListExp extends ListExp{		
		String literal;
		public void toSQL(StringBuffer buffer) {
			if(!elements.isEmpty()){
				buffer.append(literal);
				super.toSQL(buffer);
			}
		}

		public boolean cutable() {
			return false;
		}
		
	}
	
	/**
	 * 
	 * 表示Where子句
	 *
	 */
	static class WhereExp extends LiteralListExp{
		public boolean isWhereExp() {
			return true;
		}
	}
	
	static class SelectExp extends LiteralListExp{
		public boolean isSelectExp(){
			return true;
		}
	}
	
	static class FromExp extends LiteralListExp{
		
	}
	
	/**
	 * 
	 * 表示逻辑运算符,如AND,OR
	 *
	 */
	static class LogicExp extends LiteralExp{
		
		public boolean isLogicExp() {
			return true;
		}
		
	}

	static class PosTuple{
		int start;
		int end;
		
		int countParams;
		
		public PosTuple(){
			
		}
		public PosTuple(int start,int end){
			this.start = start;
			this.end = end;
		}
		
		public PosTuple(int start,int end,int countParams){
			this.start = start;
			this.end = end;
			this.countParams = countParams;
		}
		
		public int getStart() {
			return start;
		}
		public void setStart(int start) {
			this.start = start;
		}
		public int getEnd() {
			return end;
		}
		public void setEnd(int end) {
			this.end = end;
		}

		public int getCountParams() {
			return countParams;
		}

		public void setCountParams(int countParams) {
			this.countParams = countParams;
		}
		
	}

	public static Collection getParams(String[] properties, Object log) {
		ArrayList<String> params = new ArrayList<String>();
		if(log==null){
			for(int i=0;i<properties.length;i++){
				params.add(null);
			}
		}
		else{
			for(int i=0;i<properties.length;i++){
				String val = null;
				try{
					val = BeanUtils.getProperty(log, properties[i]);
				}
				catch(Exception ex){
					ex.printStackTrace();
				}
				if(String.valueOf(val).trim().length()>0){
					params.add(val);
				}
				else{
					params.add(null);
				}
			}
		}
		return params;
	}
}


package cn.com.sunrise.util;

import java.io.IOException;
import java.io.Reader;
import java.io.StreamTokenizer;
import java.io.StringReader;
import java.util.Collections;
import java.util.Map;
import java.util.Stack;

/**
 * 表达式求值器
 * 一个简单的表达式求值器,可计算带括号的数学算式
 * 如 a + b*(10 - c)/12
 * @author hegang
 *
 */
public class Evaluator {
	
	
	/**
	 * 表达式求值
	 * @param expression 简单算式
	 * @return
	 */
	public static double eval(String expression){
		return eval(expression,null);
	}
	
	/**
	 * 表达式求值
	 * 
	 * @param expression 简单算式
	 * @param params 变量名-值Map,值可以是Number或数字字符串
	 * @return
	 */
	public static double eval(String expression,Map params){
		if(expression==null || "".equals(expression.trim()))return (double)0;
		params = params==null ? Collections.EMPTY_MAP : params;
		StringReader reader=new StringReader(expression);
		try {
			return doEval(reader,params);
		} catch (IOException e) {
			e.printStackTrace(); //IOException 不会发生
		}
		return 0;
	}
	
	private static double doEval(Reader reader,Map params) throws IOException{
		StringBuffer buf=new StringBuffer();
		int c;
		while((c=reader.read())>=0){
			if(c=='('){
				buf.append(doEval(reader,params));
			}else if(c==')'){
				break;
			}else{
				buf.append((char)c);
			}
		}
		Evaluator evaluator=new Evaluator(buf.toString(),params);
		return evaluator.eval();
	}
	
	
	String expression;
	Map variables;
	
	Stack stack=new Stack();
	
	private Evaluator(String expression,Map variables){
		this.expression=expression;
		this.variables=variables;
	}
	
	double eval() {
		StreamTokenizer t= new StreamTokenizer(new StringReader(expression));
		t.ordinaryChar('/');
		t.ordinaryChar('-');
		t.ordinaryChar('*');
		t.ordinaryChar('+');
		Entry entry=null;
		while(true){
			try {
				t.nextToken();
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			switch(t.ttype){
			case StreamTokenizer.TT_NUMBER:
				entry=new Entry();
				entry.value=t.nval;
				break;
			case StreamTokenizer.TT_WORD:
				if(entry!=null && (t.sval.charAt(0)=='e' || t.sval.charAt(0)=='E')){
					entry.operator='e';					
					stack.push(entry);
					if(t.sval.length()>1){
						entry=new Entry();
						entry.value=Double.parseDouble(t.sval.substring(1));
					}else{
						entry=null;
					}
				}else{
					entry=new Entry();
					entry.value=value(t.sval);
				}
				break;
			default :
				if(entry==null && t.ttype=='-'){
					entry=new Entry();
					entry.operator='g';
					entry.value=-1;
					stack.push(entry);
					entry=null;
				}else{
					entry.operator=t.ttype;
					stack.push(entry);
					pullUp();
					entry=null;
					if(t.ttype==StreamTokenizer.TT_EOF){
						Entry result=stack.isEmpty() ? null : (Entry)stack.pop();
						return result==null ? (double)0 : result.value;
					}
				}
			}	

		}
		
		
	}
	
	void pullUp(){
		if(stack.size()<2)return;
		Entry entry2=(Entry) stack.pop();
		Entry entry1=(Entry) stack.pop();
		if(getPri(entry1.operator) > getPri(entry2.operator)){
			stack.push(mergin(entry1,entry2));
			pullUp();
		}else{
			stack.push(entry1);
			stack.push(entry2);
		}
	}
	
	/**
	 * 合并操作单元
	 * @param entry1
	 * @param entry2
	 * @return
	 */
	Entry mergin(Entry entry1,Entry entry2){
		if(!stack.isEmpty()){
			Entry entry=(Entry) stack.peek();
			//从栈顶向栈底运算,需要作运算符变换,如 2 - 1 + 3 要变为 2-(1-3)
			if(entry.operator=='-' && entry1.operator=='-'){
				entry1.operator='+';
			}else if(entry.operator=='-' && entry1.operator=='+'){
				entry1.operator='-';
			}else if(entry.operator=='/' && entry1.operator=='/'){
				entry1.operator='*';
			}else if(entry.operator=='/' && entry1.operator=='*'){
				entry1.operator='/';
			}

		}
		//操作符运算
		double value=0;
		switch(entry1.operator){
		case '+':
			value=entry1.value + entry2.value;
			break;
		case '*' :
			value=entry1.value * entry2.value;
			break;
		case '-' :
			value=entry1.value - entry2.value;
			break;
		case '/' :
			value=entry1.value / entry2.value;
			break;	
		case 'g' :
			value=entry1.value * entry2.value;
			break;
		case 'e' :
			value=entry1.value * Math.pow(10, entry2.value);
		}
		entry1.operator=entry2.operator;
		entry1.value=value;
		
		return entry1;
	}
	
	/**
	 * 获得变量值
	 * @param name
	 * @return
	 */
	double value(String name){
		Object value= variables.get(name);
		if(value==null)return 0;
		double doubleValue=0;
		if(value instanceof Number){
			doubleValue=((Number)value).doubleValue();
		}else{
			doubleValue=Double.parseDouble(value.toString());
		}
		return doubleValue;
	}
	
	
	/**
	 * 获得操作符的优先级
	 * @param opt
	 * @return
	 */
	int getPri(int opt){
		switch(opt){
		case 'g':
			return 4;
		case 'e':
			return 3;			
		case '*':
		case '/':
			return 2;
		case '+':
		case '-':
			return 1;
		case StreamTokenizer.TT_EOF:
			return 0;
		}
		return 0;
	}
	
	
	/**
	 * 
	 * 操作单元
	 *
	 */
	class Entry{
		int operator; //操作符
		double value; //值
	}
	
	
	
	
}

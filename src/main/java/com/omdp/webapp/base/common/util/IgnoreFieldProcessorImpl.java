package com.omdp.webapp.base.common.util;

import java.util.HashSet;

import net.sf.json.util.PropertyFilter;


public class IgnoreFieldProcessorImpl implements PropertyFilter {

	private HashSet<String> fieldNameSet;
	
	public IgnoreFieldProcessorImpl(String... fieldArray){
		this.fieldNameSet = new HashSet<String>();
		if(fieldArray!=null){
			for(String field:fieldArray){
				this.fieldNameSet.add(field.trim());
			}
		}
	}
	
	public boolean apply(Object source, String name, Object value) {
		if(this.fieldNameSet.contains(name)){
			return true;
		}
		else{
			return false;
		}
	}

}

package com.omdp.webapp.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "sequence")
public class Sequence implements java.io.Serializable  {

	private String name;
	private Integer currentValue;
	private Integer increment;
	private String prefix;
	
	public Sequence() {
		
	}

	public Sequence(String name, Integer currentValue, Integer increment) {
		super();
		this.name = name;
		this.currentValue = currentValue;
		this.increment = increment;
	}

	// Property accessors
	@Id
	@Column(name = "NAME", unique = true, nullable = false)
	public String getName() {
		return name;
	}

	
	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "CURRENT_VALUE", unique = true, nullable = false)
	public Integer getCurrentValue() {
		return currentValue;
	}

	
	public void setCurrentValue(Integer currentValue) {
		this.currentValue = currentValue;
	}

	@Column(name = "INCREMENT", unique = true, nullable = false)
	public Integer getIncrement() {
		return increment;
	}

	
	public void setIncrement(Integer increment) {
		this.increment = increment;
	}

	@Column(name = "PREFIX", unique = false, nullable = true)
	public String getPrefix() {
		return prefix;
	}

	
	public void setPrefix(String prefix) {
		this.prefix = prefix;
	}

	

	
	
}

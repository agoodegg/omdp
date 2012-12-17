package com.omdp.webapp.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TSysParam entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_sys_param")
public class TSysParam implements java.io.Serializable {

	// Fields

	private Integer id;
	private String paramCode;
	private String paramName;
	private String paramMemo;
	private String ftypeCode;
	private String ftypeMemo;
	private String baseType;
	private String busiCd;
	private Integer priorLvl;


	// Constructors

	/** default constructor */
	public TSysParam() {
	}

	/** minimal constructor */
	public TSysParam(String paramCode, String paramName, String ftypeCode) {
		this.paramCode = paramCode;
		this.paramName = paramName;
		this.ftypeCode = ftypeCode;
	}

	/** full constructor */
	public TSysParam(String paramCode, String paramName, String paramMemo, String ftypeCode, String ftypeMemo,
			String baseType, String busiCd, Integer priorLvl) {
		this.paramCode = paramCode;
		this.paramName = paramName;
		this.paramMemo = paramMemo;
		this.ftypeCode = ftypeCode;
		this.ftypeMemo = ftypeMemo;
		this.baseType = baseType;
		this.busiCd = busiCd;
		this.priorLvl = priorLvl;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "ID", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "PARAM_CODE", nullable = false, length = 20)
	public String getParamCode() {
		return this.paramCode;
	}

	public void setParamCode(String paramCode) {
		this.paramCode = paramCode;
	}

	@Column(name = "PARAM_NAME", nullable = false, length = 40)
	public String getParamName() {
		return this.paramName;
	}

	public void setParamName(String paramName) {
		this.paramName = paramName;
	}

	@Column(name = "PARAM_MEMO", length = 120)
	public String getParamMemo() {
		return this.paramMemo;
	}

	public void setParamMemo(String paramMemo) {
		this.paramMemo = paramMemo;
	}

	@Column(name = "FTYPE_CODE", nullable = false, length = 20)
	public String getFtypeCode() {
		return this.ftypeCode;
	}

	public void setFtypeCode(String ftypeCode) {
		this.ftypeCode = ftypeCode;
	}

	@Column(name = "FTYPE_MEMO", length = 120)
	public String getFtypeMemo() {
		return this.ftypeMemo;
	}

	public void setFtypeMemo(String ftypeMemo) {
		this.ftypeMemo = ftypeMemo;
	}

	@Column(name = "BASE_TYPE", length = 20)
	public String getBaseType() {
		return this.baseType;
	}

	public void setBaseType(String baseType) {
		this.baseType = baseType;
	}

	@Column(name = "BUSI_CD", length = 20)
	public String getBusiCd() {
		return this.busiCd;
	}

	public void setBusiCd(String busiCd) {
		this.busiCd = busiCd;
	}

	@Column(name = "PRIOR_LVL", length = 10)
	public Integer getPriorLvl() {
		return priorLvl;
	}

	
	public void setPriorLvl(Integer priorLvl) {
		this.priorLvl = priorLvl;
	}

}

package com.omdp.webapp.model;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * TSysRes entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_sys_res")
public class TSysRes implements java.io.Serializable {

	// Fields

	private Integer id;
	private String resId;
	private String resName;
	private String resTag;
	private String resPid;
	private String resDesc;
	private String resUrl;
	private String resType;
	private String resIsInvalid;
	private Date updateTime;
	private Integer resPrior;


	// Constructors

	/** default constructor */
	public TSysRes() {
	}

	/** full constructor */
	public TSysRes(String resId, String resName, String resTag, String resPid, String resDesc, String resUrl, String resType,
			String resIsInvalid, Date updateTime, Integer resPrior) {
		this.resId = resId;
		this.resName = resName;
		this.resTag = resTag;
		this.resPid = resPid;
		this.resDesc = resDesc;
		this.resUrl = resUrl;
		this.resType = resType;
		this.resIsInvalid = resIsInvalid;
		this.updateTime = updateTime;
		this.resPrior = resPrior;
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

	@Column(name = "RES_ID", length = 16)
	public String getResId() {
		return this.resId;
	}

	public void setResId(String resId) {
		this.resId = resId;
	}

	@Column(name = "RES_NAME", length = 40)
	public String getResName() {
		return this.resName;
	}

	public void setResName(String resName) {
		this.resName = resName;
	}
	
	@Column(name = "RES_TAG", length = 40)
	public String getResTag() {
		return resTag;
	}

	public void setResTag(String resTag) {
		this.resTag = resTag;
	}

	@Column(name = "RES_PID", length = 16)
	public String getResPid() {
		return this.resPid;
	}

	public void setResPid(String resPid) {
		this.resPid = resPid;
	}

	@Column(name = "RES_DESC", length = 80)
	public String getResDesc() {
		return this.resDesc;
	}

	public void setResDesc(String resDesc) {
		this.resDesc = resDesc;
	}

	@Column(name = "RES_URL", length = 80)
	public String getResUrl() {
		return this.resUrl;
	}

	public void setResUrl(String resUrl) {
		this.resUrl = resUrl;
	}

	@Column(name = "RES_TYPE", length = 16)
	public String getResType() {
		return this.resType;
	}

	public void setResType(String resType) {
		this.resType = resType;
	}

	@Column(name = "RES_IS_INVALID", length = 3)
	public String getResIsInvalid() {
		return this.resIsInvalid;
	}

	public void setResIsInvalid(String resIsInvalid) {
		this.resIsInvalid = resIsInvalid;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "UPDATE_TIME", length = 19)
	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	@Column(name = "RES_PRIOR")
	public Integer getResPrior() {
		return this.resPrior;
	}

	public void setResPrior(Integer resPrior) {
		this.resPrior = resPrior;
	}

}

package com.omdp.webapp.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * TStakeHolder entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_stake_holder")
public class TStakeHolder implements java.io.Serializable {
	
	private static final long serialVersionUID = -6570682424940589441L;
	
	public static final String ORDER="ORDER";
	public static final String ORDER_BIND="ORDER_BIND";
	public static final String ORDER_TYPE="ORDER_TYPE";
	// Fields

	private Integer id;
	private String idNo;
	private Integer uid;
	private String userName;
	private String relaType;
	private String relaStatus;
	private String relaBusi;
	private String relaValue;
	private Date createTime;
	private String creatorId;
	private String createName;


	// Constructors

	/** default constructor */
	public TStakeHolder() {
	}

	/** full constructor */
	public TStakeHolder(String idNo, Integer uid, String userName, String relaType, String relaStatus, String relaBusi,
			String relaValue, Date createTime, String creatorId, String createName) {
		this.idNo = idNo;
		this.uid = uid;
		this.userName = userName;
		this.relaType = relaType;
		this.relaStatus = relaStatus;
		this.relaBusi = relaBusi;
		this.relaValue = relaValue;
		this.createTime = createTime;
		this.creatorId = creatorId;
		this.createName = createName;
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

	@Column(name = "ID_NO", length = 20)
	public String getIdNo() {
		return this.idNo;
	}

	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}

	@Column(name = "UID")
	public Integer getUid() {
		return this.uid;
	}

	public void setUid(Integer uid) {
		this.uid = uid;
	}

	@Column(name = "USER_NAME", length = 40)
	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Column(name = "RELA_TYPE", length = 20)
	public String getRelaType() {
		return this.relaType;
	}

	public void setRelaType(String relaType) {
		this.relaType = relaType;
	}

	@Column(name = "RELA_STATUS", length = 20)
	public String getRelaStatus() {
		return this.relaStatus;
	}

	public void setRelaStatus(String relaStatus) {
		this.relaStatus = relaStatus;
	}

	@Column(name = "RELA_BUSI", length = 20)
	public String getRelaBusi() {
		return this.relaBusi;
	}

	public void setRelaBusi(String relaBusi) {
		this.relaBusi = relaBusi;
	}

	@Column(name = "RELA_VALUE", length = 40)
	public String getRelaValue() {
		return this.relaValue;
	}

	public void setRelaValue(String relaValue) {
		this.relaValue = relaValue;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "CREATE_TIME", length = 19)
	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Column(name = "CREATOR_ID", length = 20)
	public String getCreatorId() {
		return this.creatorId;
	}

	public void setCreatorId(String creatorId) {
		this.creatorId = creatorId;
	}

	@Column(name = "CREATE_NAME", length = 40)
	public String getCreateName() {
		return this.createName;
	}

	public void setCreateName(String createName) {
		this.createName = createName;
	}

}

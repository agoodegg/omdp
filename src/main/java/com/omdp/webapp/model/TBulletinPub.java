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
 * TBulletinPub entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_bulletin_pub")
public class TBulletinPub implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer bulletId;
	private String pubType;
	private String roleId;
	private String userIdNo;
	private Date pubTime;
	private Date validateTime;


	// Constructors

	/** default constructor */
	public TBulletinPub() {
	}

	/** minimal constructor */
	public TBulletinPub(Integer id, Integer bulletId, String pubType, Date pubTime) {
		this.id = id;
		this.bulletId = bulletId;
		this.pubType = pubType;
		this.pubTime = pubTime;
	}

	/** full constructor */
	public TBulletinPub(Integer id, Integer bulletId, String pubType, String roleId, String userIdNo, Date pubTime,
			Date validateTime) {
		this.id = id;
		this.bulletId = bulletId;
		this.pubType = pubType;
		this.roleId = roleId;
		this.userIdNo = userIdNo;
		this.pubTime = pubTime;
		this.validateTime = validateTime;
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

	@Column(name = "BULLET_ID", nullable = false)
	public Integer getBulletId() {
		return this.bulletId;
	}

	public void setBulletId(Integer bulletId) {
		this.bulletId = bulletId;
	}

	@Column(name = "PUB_TYPE", nullable = false, length = 10)
	public String getPubType() {
		return this.pubType;
	}

	public void setPubType(String pubType) {
		this.pubType = pubType;
	}

	@Column(name = "ROLE_ID", length = 16)
	public String getRoleId() {
		return this.roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	@Column(name = "USER_ID_NO", length = 20)
	public String getUserIdNo() {
		return this.userIdNo;
	}

	public void setUserIdNo(String userIdNo) {
		this.userIdNo = userIdNo;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "PUB_TIME", nullable = false, length = 10)
	public Date getPubTime() {
		return this.pubTime;
	}

	public void setPubTime(Date pubTime) {
		this.pubTime = pubTime;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "VALIDATE_TIME", length = 10)
	public Date getValidateTime() {
		return this.validateTime;
	}

	public void setValidateTime(Date validateTime) {
		this.validateTime = validateTime;
	}

}

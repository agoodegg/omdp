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
import javax.persistence.Transient;

/**
 * TBulletinInfo entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_bulletin_info")
public class TBulletinInfo implements java.io.Serializable {

	// Fields

	private Integer id;
	private String title;
	private String content;
	private String creator;
	private Date createTime;
	private String bulletinStatus;
	private String showType;
	private String buType;
	private String buLvl;
	private Date endTime;
	
	
	
	@Transient
	private String endTimeStr;
	
	@Transient
	private String creatorName;


	// Constructors

	/** default constructor */
	public TBulletinInfo() {
	}

	/** minimal constructor */
	public TBulletinInfo(Integer id, String title, String creator, Date createTime) {
		this.id = id;
		this.title = title;
		this.creator = creator;
		this.createTime = createTime;
	}

	/** full constructor */
	public TBulletinInfo(Integer id, String title, String content, String creator, Date createTime,
			String bulletinStatus, String showType, String buType, String buLvl, Date endTime) {
		this.id = id;
		this.title = title;
		this.content = content;
		this.creator = creator;
		this.createTime = createTime;
		this.bulletinStatus = bulletinStatus;
		this.showType = showType;
		this.buType = buType;
		this.buLvl = buLvl;
		this.endTime = endTime;
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

	@Column(name = "TITLE", nullable = false, length = 160)
	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name = "CONTENT", length = 65535)
	public String getContent() {
		return this.content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Column(name = "CREATOR", nullable = false, length = 40)
	public String getCreator() {
		return this.creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATE_TIME", nullable = false, length = 10)
	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Column(name = "BULLETIN_STATUS", length = 10)
	public String getBulletinStatus() {
		return this.bulletinStatus;
	}

	public void setBulletinStatus(String bulletinStatus) {
		this.bulletinStatus = bulletinStatus;
	}

	@Column(name = "SHOW_TYPE", length = 10)
	public String getShowType() {
		return this.showType;
	}

	public void setShowType(String showType) {
		this.showType = showType;
	}

	@Column(name = "BU_TYPE", length = 20)
	public String getBuType() {
		return this.buType;
	}

	public void setBuType(String buType) {
		this.buType = buType;
	}

	@Column(name = "BU_LVL", length = 20)
	public String getBuLvl() {
		return this.buLvl;
	}

	public void setBuLvl(String buLvl) {
		this.buLvl = buLvl;
	}


	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "END_TIME", nullable = false, length = 10)
	public Date getEndTime() {
		return endTime;
	}

	
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	
	@Transient
	public String getEndTimeStr() {
		return endTimeStr;
	}

	
	public void setEndTimeStr(String endTimeStr) {
		this.endTimeStr = endTimeStr;
	}

	
	@Transient
	public String getCreatorName() {
		return creatorName;
	}

	
	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}

}

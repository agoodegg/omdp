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
 * TUserLogin entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_user_login")
public class TUserLogin implements java.io.Serializable {

	// Fields

	private Integer id;
	private String userId;
	private String userName;
	private String logType;
	private String ipAddress;
	private Date logTime;
	private String addInfo;
	
	private String logTimeU;
	private String logTimeD;


	// Constructors

	/** default constructor */
	public TUserLogin() {
	}

	/** full constructor */
	public TUserLogin(String userId, String userName, String logType, String ipAddress, Date logTime, String addInfo) {
		this.userId = userId;
		this.userName = userName;
		this.logType = logType;
		this.ipAddress = ipAddress;
		this.logTime = logTime;
		this.addInfo = addInfo;
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

	@Column(name = "USER_ID", length = 20)
	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	@Column(name = "USER_NAME", length = 40)
	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Column(name = "LOG_TYPE", length = 10)
	public String getLogType() {
		return logType;
	}

	
	public void setLogType(String logType) {
		this.logType = logType;
	}
	
	@Column(name = "IP_ADDRESS", length = 40)
	public String getIpAddress() {
		return this.ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "LOG_TIME", length = 19)
	public Date getLogTime() {
		return this.logTime;
	}

	public void setLogTime(Date logTime) {
		this.logTime = logTime;
	}

	@Column(name = "ADD_INFO", length = 60)
	public String getAddInfo() {
		return this.addInfo;
	}

	public void setAddInfo(String addInfo) {
		this.addInfo = addInfo;
	}

	
	@Transient
	public String getLogTimeU() {
		return logTimeU;
	}

	
	public void setLogTimeU(String logTimeU) {
		this.logTimeU = logTimeU;
	}

	@Transient
	public String getLogTimeD() {
		return logTimeD;
	}

	
	public void setLogTimeD(String logTimeD) {
		this.logTimeD = logTimeD;
	}

	
}

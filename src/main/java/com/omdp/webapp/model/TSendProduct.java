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
 * TSendProduct entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_send_product")
public class TSendProduct implements java.io.Serializable {

	// Fields

	private Integer id;
	private String sendNo;
	private String ordernum;
	private Date createTime;
	private String custName;
	private String tel;
	private Double money;
	private String moneyDesc;
	private String sendMemo;
	private String doneFlag;
	private String sendOpr;
	private String status;
	private Date doneTime;
	private Date sendTime;
	
	private String createTimeU;
	private String createTimeD;


	// Constructors

	/** default constructor */
	public TSendProduct() {
	}

	/** full constructor */
	public TSendProduct(String sendNo, String ordernum, Date createTime, String custName, String tel, Double money,
			String moneyDesc, String sendMemo, String doneFlag, String sendOpr, String status, Date doneTime, Date sendTime) {
		this.sendNo = sendNo;
		this.ordernum = ordernum;
		this.createTime = createTime;
		this.custName = custName;
		this.tel = tel;
		this.money = money;
		this.moneyDesc = moneyDesc;
		this.sendMemo = sendMemo;
		this.doneFlag = doneFlag;
		this.sendOpr = sendOpr;
		this.status = status;
		this.doneTime = doneTime;
		this.sendTime = sendTime;
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

	@Column(name = "SEND_NO", length = 20)
	public String getSendNo() {
		return this.sendNo;
	}

	public void setSendNo(String sendNo) {
		this.sendNo = sendNo;
	}

	@Column(name = "ORDERNUM", length = 20)
	public String getOrdernum() {
		return this.ordernum;
	}

	public void setOrdernum(String ordernum) {
		this.ordernum = ordernum;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATE_TIME", length = 19)
	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Column(name = "CUST_NAME", length = 40)
	public String getCustName() {
		return this.custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	@Column(name = "TEL", length = 40)
	public String getTel() {
		return this.tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	@Column(name = "MONEY", precision = 12)
	public Double getMoney() {
		return this.money;
	}

	public void setMoney(Double money) {
		this.money = money;
	}

	@Column(name = "MONEY_DESC", length = 30)
	public String getMoneyDesc() {
		return this.moneyDesc;
	}

	public void setMoneyDesc(String moneyDesc) {
		this.moneyDesc = moneyDesc;
	}

	@Column(name = "SEND_MEMO", length = 200)
	public String getSendMemo() {
		return this.sendMemo;
	}

	public void setSendMemo(String sendMemo) {
		this.sendMemo = sendMemo;
	}

	@Column(name = "DONE_FLAG", length = 20)
	public String getDoneFlag() {
		return this.doneFlag;
	}

	public void setDoneFlag(String doneFlag) {
		this.doneFlag = doneFlag;
	}

	@Column(name = "SEND_OPR", length = 40)
	public String getSendOpr() {
		return this.sendOpr;
	}

	public void setSendOpr(String sendOpr) {
		this.sendOpr = sendOpr;
	}

	@Column(name = "STATUS", length = 20)
	public String getStatus() {
		return this.status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DONE_TIME", length = 19)
	public Date getDoneTime() {
		return this.doneTime;
	}

	public void setDoneTime(Date doneTime) {
		this.doneTime = doneTime;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "SEND_TIME", length = 19)
	public Date getSendTime() {
		return sendTime;
	}

	
	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}

	@Transient
	public String getCreateTimeU() {
		return createTimeU;
	}

	
	public void setCreateTimeU(String createTimeU) {
		this.createTimeU = createTimeU;
	}

	@Transient
	public String getCreateTimeD() {
		return createTimeD;
	}

	
	public void setCreateTimeD(String createTimeD) {
		this.createTimeD = createTimeD;
	}

	
}

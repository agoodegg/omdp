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
 * TTradInfo entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_trad_info")
public class TTradInfo implements java.io.Serializable {

	// Fields

	private Integer id;
	private String ordernum;
	private String tradCd;
	private String custCd;
	private String custName;
	private String tel;
	private String custAddress;
	private Date tradTime;
	private Double yfee;
	private Double dfee;
	private Double offFee;
	private Double acFee;
	private String feeType;
	private String feeStatus;
	private String checkNum;
	private String checkStatus;
	private String checkType;
	private String fpnum;
	private String tradOpr;
	private String tradOprName;
	private String tradComm;
	private String validStatus;
	private String validComm;
	private String validOpr;
	private String validOprName;
	private Date validTime;

	@Transient
	private String orderStatusCn;
	@Transient
	private String orderStatus;
	

	// Constructors

	/** default constructor */
	public TTradInfo() {
	}

	/** minimal constructor */
	public TTradInfo(String ordernum, String tradCd, String custCd, Double yfee, Double acFee, String tradOpr) {
		this.ordernum = ordernum;
		this.tradCd = tradCd;
		this.custCd = custCd;
		this.yfee = yfee;
		this.acFee = acFee;
		this.tradOpr = tradOpr;
	}

	/** full constructor */
	public TTradInfo(String ordernum, String tradCd, String custCd, String custName, String tel, String custAddress,
			Date tradTime, Double yfee, Double dfee, Double offFee, Double acFee, String feeType, String feeStatus,
			String checkNum, String checkStatus, String checkType, String fpnum, String tradOpr, String tradOprName,
			String tradComm, String validStatus, String validComm, String validOpr, String validOprName, Date validTime) {
		this.ordernum = ordernum;
		this.tradCd = tradCd;
		this.custCd = custCd;
		this.custName = custName;
		this.tel = tel;
		this.custAddress = custAddress;
		this.tradTime = tradTime;
		this.yfee = yfee;
		this.dfee = dfee;
		this.offFee = offFee;
		this.acFee = acFee;
		this.feeType = feeType;
		this.feeStatus = feeStatus;
		this.checkNum = checkNum;
		this.checkStatus = checkStatus;
		this.checkType = checkType;
		this.fpnum = fpnum;
		this.tradOpr = tradOpr;
		this.tradOprName = tradOprName;
		this.tradComm = tradComm;
		this.validStatus = validStatus;
		this.validComm = validComm;
		this.validOpr = validOpr;
		this.validOprName = validOprName;
		this.validTime = validTime;
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

	@Column(name = "ORDERNUM", nullable = false, length = 20)
	public String getOrdernum() {
		return this.ordernum;
	}

	public void setOrdernum(String ordernum) {
		this.ordernum = ordernum;
	}

	@Column(name = "TRAD_CD", nullable = false, length = 20)
	public String getTradCd() {
		return this.tradCd;
	}

	public void setTradCd(String tradCd) {
		this.tradCd = tradCd;
	}

	@Column(name = "CUST_CD", nullable = false, length = 20)
	public String getCustCd() {
		return this.custCd;
	}

	public void setCustCd(String custCd) {
		this.custCd = custCd;
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

	@Column(name = "CUST_ADDRESS", length = 100)
	public String getCustAddress() {
		return this.custAddress;
	}

	public void setCustAddress(String custAddress) {
		this.custAddress = custAddress;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "TRAD_TIME", length = 19)
	public Date getTradTime() {
		return this.tradTime;
	}

	public void setTradTime(Date tradTime) {
		this.tradTime = tradTime;
	}

	@Column(name = "YFEE", nullable = false, precision = 12)
	public Double getYfee() {
		return this.yfee;
	}

	public void setYfee(Double yfee) {
		this.yfee = yfee;
	}

	@Column(name = "DFEE", precision = 12)
	public Double getDfee() {
		return this.dfee;
	}

	public void setDfee(Double dfee) {
		this.dfee = dfee;
	}

	@Column(name = "OFF_FEE", precision = 12)
	public Double getOffFee() {
		return this.offFee;
	}

	public void setOffFee(Double offFee) {
		this.offFee = offFee;
	}

	@Column(name = "AC_FEE", nullable = false, precision = 12)
	public Double getAcFee() {
		return this.acFee;
	}

	public void setAcFee(Double acFee) {
		this.acFee = acFee;
	}

	@Column(name = "FEE_TYPE", length = 20)
	public String getFeeType() {
		return this.feeType;
	}

	public void setFeeType(String feeType) {
		this.feeType = feeType;
	}

	@Column(name = "FEE_STATUS", length = 20)
	public String getFeeStatus() {
		return this.feeStatus;
	}

	public void setFeeStatus(String feeStatus) {
		this.feeStatus = feeStatus;
	}

	@Column(name = "CHECK_NUM", length = 30)
	public String getCheckNum() {
		return this.checkNum;
	}

	public void setCheckNum(String checkNum) {
		this.checkNum = checkNum;
	}

	@Column(name = "CHECK_STATUS", length = 30)
	public String getCheckStatus() {
		return this.checkStatus;
	}

	public void setCheckStatus(String checkStatus) {
		this.checkStatus = checkStatus;
	}

	@Column(name = "CHECK_TYPE", length = 30)
	public String getCheckType() {
		return this.checkType;
	}

	public void setCheckType(String checkType) {
		this.checkType = checkType;
	}

	@Column(name = "FPNUM", length = 30)
	public String getFpnum() {
		return this.fpnum;
	}

	public void setFpnum(String fpnum) {
		this.fpnum = fpnum;
	}

	@Column(name = "TRAD_OPR", nullable = false, length = 20)
	public String getTradOpr() {
		return this.tradOpr;
	}

	public void setTradOpr(String tradOpr) {
		this.tradOpr = tradOpr;
	}

	@Column(name = "TRAD_OPR_NAME", length = 40)
	public String getTradOprName() {
		return this.tradOprName;
	}

	public void setTradOprName(String tradOprName) {
		this.tradOprName = tradOprName;
	}

	@Column(name = "TRAD_COMM", length = 200)
	public String getTradComm() {
		return this.tradComm;
	}

	public void setTradComm(String tradComm) {
		this.tradComm = tradComm;
	}

	@Column(name = "VALID_STATUS", length = 20)
	public String getValidStatus() {
		return this.validStatus;
	}

	public void setValidStatus(String validStatus) {
		this.validStatus = validStatus;
	}

	@Column(name = "VALID_COMM", length = 200)
	public String getValidComm() {
		return this.validComm;
	}

	public void setValidComm(String validComm) {
		this.validComm = validComm;
	}

	@Column(name = "VALID_OPR", length = 20)
	public String getValidOpr() {
		return this.validOpr;
	}

	public void setValidOpr(String validOpr) {
		this.validOpr = validOpr;
	}

	@Column(name = "VALID_OPR_NAME", length = 40)
	public String getValidOprName() {
		return this.validOprName;
	}

	public void setValidOprName(String validOprName) {
		this.validOprName = validOprName;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "VALID_TIME", length = 19)
	public Date getValidTime() {
		return this.validTime;
	}

	public void setValidTime(Date validTime) {
		this.validTime = validTime;
	}

	
	@Transient
	public String getOrderStatusCn() {
		return orderStatusCn;
	}

	
	public void setOrderStatusCn(String orderStatusCn) {
		this.orderStatusCn = orderStatusCn;
	}

	@Transient
	public String getOrderStatus() {
		return orderStatus;
	}

	
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	
	
}

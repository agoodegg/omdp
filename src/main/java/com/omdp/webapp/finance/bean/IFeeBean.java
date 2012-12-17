package com.omdp.webapp.finance.bean;

import java.util.Date;


public class IFeeBean {

	private String ordernum;
	private String uptime;
	private String downtime;
	private String ifeeType;
	private String payType;
	private String busiType;
	private double fee;
	private double yfee;
	private double acFee;
	private double payFee;
	private Date orderTime;
	private String opr;
	private Date tradTime;
	private Integer orderid;
	
	private String custName;
	private String custId;
	private String mobileNo;
	
	private double totalYfee;
	private double totalAcFee;
	
	private String dispatchOther;
	
	private String feeType;
	
	private String ext1;
	
	private String year;
	
	private String montn;
	
	private double monthValue;
	
	
	public IFeeBean(){
		
	}


	
	public String getOrdernum() {
		return ordernum;
	}


	
	public void setOrdernum(String ordernum) {
		this.ordernum = ordernum;
	}


	
	public String getUptime() {
		return uptime;
	}


	
	public void setUptime(String uptime) {
		this.uptime = uptime;
	}


	
	public String getDowntime() {
		return downtime;
	}


	
	public void setDowntime(String downtime) {
		this.downtime = downtime;
	}


	
	public String getIfeeType() {
		return ifeeType;
	}


	
	public void setIfeeType(String ifeeType) {
		this.ifeeType = ifeeType;
	}


	
	public String getPayType() {
		return payType;
	}


	
	public void setPayType(String payType) {
		this.payType = payType;
	}


	
	public String getBusiType() {
		return busiType;
	}


	
	public void setBusiType(String busiType) {
		this.busiType = busiType;
	}


	
	public double getFee() {
		return fee;
	}


	
	public void setFee(double fee) {
		this.fee = fee;
	}



	
	public String getExt1() {
		return ext1;
	}



	
	public void setExt1(String ext1) {
		this.ext1 = ext1;
	}



	
	public String getDispatchOther() {
		return dispatchOther;
	}



	
	public void setDispatchOther(String dispatchOther) {
		this.dispatchOther = dispatchOther;
	}



	
	public double getAcFee() {
		return acFee;
	}



	
	public void setAcFee(double acFee) {
		this.acFee = acFee;
	}



	
	public double getPayFee() {
		return payFee;
	}



	
	public void setPayFee(double payFee) {
		this.payFee = payFee;
	}



	
	public double getYfee() {
		return yfee;
	}



	
	public void setYfee(double yfee) {
		this.yfee = yfee;
	}


	public Date getOrderTime() {
		return orderTime;
	}

	
	public void setOrderTime(Date orderTime) {
		this.orderTime = orderTime;
	}

	
	public String getOpr() {
		return opr;
	}

	
	public void setOpr(String opr) {
		this.opr = opr;
	}

	
	public Date getTradTime() {
		return tradTime;
	}

	
	public void setTradTime(Date tradTime) {
		this.tradTime = tradTime;
	}

	
	public Integer getOrderid() {
		return orderid;
	}

	
	public void setOrderid(Integer orderid) {
		this.orderid = orderid;
	}



	
	public double getTotalYfee() {
		return totalYfee;
	}



	
	public void setTotalYfee(double totalYfee) {
		this.totalYfee = totalYfee;
	}



	
	public double getTotalAcFee() {
		return totalAcFee;
	}



	
	public void setTotalAcFee(double totalAcFee) {
		this.totalAcFee = totalAcFee;
	}



	
	public String getCustName() {
		return custName;
	}



	
	public void setCustName(String custName) {
		this.custName = custName;
	}



	
	public String getMobileNo() {
		return mobileNo;
	}



	
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}



	
	public String getCustId() {
		return custId;
	}



	
	public void setCustId(String custId) {
		this.custId = custId;
	}



	
	public String getYear() {
		return year;
	}



	
	public void setYear(String year) {
		this.year = year;
	}



	
	public String getMontn() {
		return montn;
	}



	
	public void setMontn(String montn) {
		this.montn = montn;
	}



	
	public double getMonthValue() {
		return monthValue;
	}



	
	public void setMonthValue(double monthValue) {
		this.monthValue = monthValue;
	}



	
	public String getFeeType() {
		return feeType;
	}



	
	public void setFeeType(String feeType) {
		this.feeType = feeType;
	}

	
}

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
 * TCustInfo entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_cust_info")
public class TCustInfo implements java.io.Serializable {

	// Fields

	private Integer id;
	private String custId;
	private String shortName;
	private String fullName;
	private String pinyin;
	private String custLvl;
	private String source;
	private String fax;
	private String creditLvl;
	private String payType;
	private String billType;
	private String payCycle;
	private Double credit;
	private String custType;
	private String mobileNo;
	private String phoneNo;
	private String email;
	private String zoneCd;
	private String zoneName;
	private String userId;
	private String busiType1;
	private String busiType2;
	private String province;
	private String city;
	private String address;
	private String webSite;
	private String intro;
	private Date createTime;
	private String oprId;
	private String status;

	private String userName;
	private String oprName;
	
	private Double statFee;
	private String uptime;
	private String downtime;

	// Constructors

	/** default constructor */
	public TCustInfo() {
	}

	/** full constructor */
	public TCustInfo(String custId, String shortName, String fullName, String pinyin, String custLvl, String source,
			String fax, String creditLvl, String payType, String billType, String payCycle, Double credit,
			String custType, String mobileNo, String phoneNo, String email, String zoneCd, String zoneName,
			String userId, String busiType1, String busiType2, String province, String city, String address, String webSite, String intro, Date createTime, String oprId, String status) {
		this.custId = custId;
		this.shortName = shortName;
		this.fullName = fullName;
		this.pinyin = pinyin;
		this.custLvl = custLvl;
		this.source = source;
		this.fax = fax;
		this.creditLvl = creditLvl;
		this.payType = payType;
		this.billType = billType;
		this.payCycle = payCycle;
		this.credit = credit;
		this.custType = custType;
		this.mobileNo = mobileNo;
		this.phoneNo = phoneNo;
		this.email = email;
		this.zoneCd = zoneCd;
		this.zoneName = zoneName;
		this.userId = userId;
		this.busiType1 = busiType1;
		this.busiType2 = busiType2;
		this.province = province;
		this.city = city;
		this.address = address;
		this.webSite = webSite;
		this.intro = intro;
		this.createTime = createTime;
		this.oprId = oprId;
		this.status = status;
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

	@Column(name = "CUST_ID", length = 20)
	public String getCustId() {
		return this.custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	@Column(name = "SHORT_NAME", length = 40)
	public String getShortName() {
		return this.shortName;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	@Column(name = "FULL_NAME", length = 80)
	public String getFullName() {
		return this.fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	@Column(name = "PINYIN", length = 40)
	public String getPinyin() {
		return this.pinyin;
	}

	public void setPinyin(String pinyin) {
		this.pinyin = pinyin;
	}

	@Column(name = "CUST_LVL", length = 20)
	public String getCustLvl() {
		return this.custLvl;
	}

	public void setCustLvl(String custLvl) {
		this.custLvl = custLvl;
	}

	@Column(name = "SOURCE", length = 20)
	public String getSource() {
		return this.source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	@Column(name = "FAX", length = 40)
	public String getFax() {
		return this.fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	@Column(name = "CREDIT_LVL", length = 20)
	public String getCreditLvl() {
		return this.creditLvl;
	}

	public void setCreditLvl(String creditLvl) {
		this.creditLvl = creditLvl;
	}

	@Column(name = "PAY_TYPE", length = 20)
	public String getPayType() {
		return this.payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	@Column(name = "BILL_TYPE", length = 20)
	public String getBillType() {
		return this.billType;
	}

	public void setBillType(String billType) {
		this.billType = billType;
	}

	@Column(name = "PAY_CYCLE", length = 20)
	public String getPayCycle() {
		return this.payCycle;
	}

	public void setPayCycle(String payCycle) {
		this.payCycle = payCycle;
	}

	@Column(name = "CREDIT", precision = 12)
	public Double getCredit() {
		return this.credit;
	}

	public void setCredit(Double credit) {
		this.credit = credit;
	}

	@Column(name = "CUST_TYPE", length = 20)
	public String getCustType() {
		return this.custType;
	}

	public void setCustType(String custType) {
		this.custType = custType;
	}

	@Column(name = "MOBILE_NO", length = 40)
	public String getMobileNo() {
		return this.mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	@Column(name = "PHONE_NO", length = 40)
	public String getPhoneNo() {
		return this.phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	@Column(name = "EMAIL", length = 60)
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "ZONE_CD", length = 10)
	public String getZoneCd() {
		return this.zoneCd;
	}

	public void setZoneCd(String zoneCd) {
		this.zoneCd = zoneCd;
	}

	@Column(name = "ZONE_NAME", length = 30)
	public String getZoneName() {
		return this.zoneName;
	}

	public void setZoneName(String zoneName) {
		this.zoneName = zoneName;
	}

	@Column(name = "USER_ID", length = 20)
	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	@Column(name = "BUSI_TYPE1", length = 40)
	public String getBusiType1() {
		return busiType1;
	}

	
	public void setBusiType1(String busiType1) {
		this.busiType1 = busiType1;
	}

	@Column(name = "BUSI_TYPE2", length = 40)
	public String getBusiType2() {
		return busiType2;
	}

	
	public void setBusiType2(String busiType2) {
		this.busiType2 = busiType2;
	}

	@Column(name = "PROVINCE", length = 30)
	public String getProvince() {
		return province;
	}

	
	public void setProvince(String province) {
		this.province = province;
	}

	@Column(name = "CITY", length = 30)
	public String getCity() {
		return city;
	}

	
	public void setCity(String city) {
		this.city = city;
	}

	@Column(name = "ADDRESS", length = 100)
	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Column(name = "WEB_SITE", length = 80)
	public String getWebSite() {
		return this.webSite;
	}

	public void setWebSite(String webSite) {
		this.webSite = webSite;
	}

	@Column(name = "INTRO", length = 120)
	public String getIntro() {
		return this.intro;
	}

	public void setIntro(String intro) {
		this.intro = intro;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATE_TIME", length = 19)
	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Column(name = "OPR_ID", length = 20)
	public String getOprId() {
		return this.oprId;
	}

	public void setOprId(String oprId) {
		this.oprId = oprId;
	}
	
	@Column(name = "STATUS", length = 10)
	public String getStatus() {
		return status;
	}

	
	public void setStatus(String status) {
		this.status = status;
	}

	@Transient
	public String getUserName() {
		return userName;
	}

	
	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Transient
	public String getOprName() {
		return oprName;
	}

	
	public void setOprName(String oprName) {
		this.oprName = oprName;
	}

	@Transient
	public Double getStatFee() {
		return statFee;
	}

	
	public void setStatFee(Double statFee) {
		this.statFee = statFee;
	}

	@Transient
	public String getUptime() {
		return uptime;
	}

	
	public void setUptime(String uptime) {
		this.uptime = uptime;
	}

	@Transient
	public String getDowntime() {
		return downtime;
	}

	
	public void setDowntime(String downtime) {
		this.downtime = downtime;
	}

	
	
}

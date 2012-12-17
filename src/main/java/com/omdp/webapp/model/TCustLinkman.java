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
 * TCustLinkman entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_cust_linkman")
public class TCustLinkman implements java.io.Serializable {

	// Fields

	private Integer id;
	private String custId;
	private String linkMan;
	private String dept;
	private String principalship;
	private String immobilityPhone;
	private String mobilePhone;
	private String email;
	private Date createTime;


	// Constructors

	/** default constructor */
	public TCustLinkman() {
	}

	/** full constructor */
	public TCustLinkman(String custId, String linkMan, String dept, String principalship, String immobilityPhone,
			String mobilePhone, String email, Date createTime) {
		this.custId = custId;
		this.linkMan = linkMan;
		this.dept = dept;
		this.principalship = principalship;
		this.immobilityPhone = immobilityPhone;
		this.mobilePhone = mobilePhone;
		this.email = email;
		this.createTime = createTime;
	}

	// Property accessors
	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "CUST_ID", nullable = false, length = 20)
	public String getCustId() {
		return this.custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	@Column(name = "linkMan", nullable = false, length = 40)
	public String getLinkMan() {
		return this.linkMan;
	}

	public void setLinkMan(String linkMan) {
		this.linkMan = linkMan;
	}

	@Column(name = "dept", nullable = false, length = 20)
	public String getDept() {
		return this.dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

	@Column(name = "principalship", nullable = false, length = 20)
	public String getPrincipalship() {
		return this.principalship;
	}

	public void setPrincipalship(String principalship) {
		this.principalship = principalship;
	}

	@Column(name = "immobility_Phone", nullable = false, length = 20)
	public String getImmobilityPhone() {
		return this.immobilityPhone;
	}

	public void setImmobilityPhone(String immobilityPhone) {
		this.immobilityPhone = immobilityPhone;
	}

	@Column(name = "MOBILE_PHONE", nullable = false, length = 20)
	public String getMobilePhone() {
		return this.mobilePhone;
	}

	public void setMobilePhone(String mobilePhone) {
		this.mobilePhone = mobilePhone;
	}

	@Column(name = "EMAIL", nullable = false, length = 20)
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "CREATE_TIME", nullable = false, length = 19)
	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}

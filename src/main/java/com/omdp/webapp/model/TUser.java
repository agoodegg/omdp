package com.omdp.webapp.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import org.apache.commons.lang.StringUtils;
import org.springframework.security.GrantedAuthority;
import org.springframework.security.GrantedAuthorityImpl;
import org.springframework.security.acls.Permission;
import org.springframework.security.userdetails.UserDetails;

import com.omdp.webapp.security.AclBean;

/**
 * TUser entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_user")
public class TUser implements java.io.Serializable, UserDetails, AclBean {

	// Fields

	private Integer id;
	private String idNo;
	private String userAccount;
	private String userName;
	private String userPwd;
	private String deptCd;
	private Date pwdUpdateTime;
	private String question;
	private String answer;
	private String gender;
	private Integer age;
	private String email;
	private String address;
	private String postcode;
	private String mobileNo;
	private String phoneNo;
	private String ALinkMan;
	private String ALinkPhone;
	private String memoDesc;
	private Date updateTime;
	private String isInvalid;

	private String vcode;

	private String oldPwd;
	private String newPwd1;
	private String newPwd2;
	
	private String ipAddress;
	
	private String ids;

	/** 保存该用户对应的roleId */
	private List<String> userInRoleIds = new ArrayList<String>();

	/** 保存该用户对应的组织架构的roleid */
	private List<String> userInOrgRoleIds = new ArrayList<String>();

	private Permission permission;


	@Transient
	private String roleIds;
	@Transient
	private String roleNames;
	@Transient
	private String dispatchOther;
	@Transient
	private String payType;
	
	private Double achFee;
	private Integer clientNum;
	private String uptime;
	private String downtime;
	
	// Constructors

	/** default constructor */
	public TUser() {
	}

	/** full constructor */
	public TUser(String idNo, String userAccount, String userName, String userPwd, String deptCd, Date pwdUpdateTime,
			String question, String answer, String gender, Integer age, String email, String address, String postcode,
			String mobileNo, String phoneNo, String ALinkMan, String ALinkPhone, String memoDesc, Date updateTime,
			String isInvalid) {
		this.idNo = idNo;
		this.userAccount = userAccount;
		this.userName = userName;
		this.userPwd = userPwd;
		this.deptCd = deptCd;
		this.pwdUpdateTime = pwdUpdateTime;
		this.question = question;
		this.answer = answer;
		this.gender = gender;
		this.age = age;
		this.email = email;
		this.address = address;
		this.postcode = postcode;
		this.mobileNo = mobileNo;
		this.phoneNo = phoneNo;
		this.ALinkMan = ALinkMan;
		this.ALinkPhone = ALinkPhone;
		this.memoDesc = memoDesc;
		this.updateTime = updateTime;
		this.isInvalid = isInvalid;
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

	@Column(name = "USER_ACCOUNT", length = 40)
	public String getUserAccount() {
		return this.userAccount;
	}

	public void setUserAccount(String userAccount) {
		this.userAccount = userAccount;
	}

	@Column(name = "USER_NAME", length = 40)
	public String getUserName() {
		return this.userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Column(name = "USER_PWD", length = 40)
	public String getUserPwd() {
		return this.userPwd;
	}

	public void setUserPwd(String userPwd) {
		this.userPwd = userPwd;
	}

	@Column(name = "DEPT_CD", length = 20)
	public String getDeptCd() {
		return this.deptCd;
	}

	public void setDeptCd(String deptCd) {
		this.deptCd = deptCd;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "PWD_UPDATE_TIME", length = 19)
	public Date getPwdUpdateTime() {
		return this.pwdUpdateTime;
	}

	public void setPwdUpdateTime(Date pwdUpdateTime) {
		this.pwdUpdateTime = pwdUpdateTime;
	}

	@Column(name = "QUESTION", length = 40)
	public String getQuestion() {
		return this.question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	@Column(name = "ANSWER", length = 40)
	public String getAnswer() {
		return this.answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	@Column(name = "GENDER", length = 8)
	public String getGender() {
		return this.gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	@Column(name = "AGE")
	public Integer getAge() {
		return this.age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	@Column(name = "EMAIL", length = 60)
	public String getEmail() {
		return this.email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Column(name = "ADDRESS", length = 100)
	public String getAddress() {
		return this.address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Column(name = "POSTCODE", length = 10)
	public String getPostcode() {
		return this.postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	@Column(name = "MOBILE_NO", length = 20)
	public String getMobileNo() {
		return this.mobileNo;
	}

	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	@Column(name = "PHONE_NO", length = 20)
	public String getPhoneNo() {
		return this.phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	@Column(name = "A_LINK_MAN", length = 40)
	public String getALinkMan() {
		return this.ALinkMan;
	}

	public void setALinkMan(String ALinkMan) {
		this.ALinkMan = ALinkMan;
	}

	@Column(name = "A_LINK_PHONE", length = 20)
	public String getALinkPhone() {
		return this.ALinkPhone;
	}

	public void setALinkPhone(String ALinkPhone) {
		this.ALinkPhone = ALinkPhone;
	}

	@Column(name = "MEMO_DESC", length = 200)
	public String getMemoDesc() {
		return this.memoDesc;
	}

	public void setMemoDesc(String memoDesc) {
		this.memoDesc = memoDesc;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "UPDATE_TIME", length = 19)
	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	@Column(name = "IS_INVALID", length = 2)
	public String getIsInvalid() {
		return this.isInvalid;
	}

	public void setIsInvalid(String isInvalid) {
		this.isInvalid = isInvalid;
	}

	@Transient
	public String getVcode() {
		return vcode;
	}

	public void setVcode(String vcode) {
		this.vcode = vcode;
	}

	// -----实现UserDetails的接口，通过spring security对用户权限进行管理
	/*
	 * 返回RoleId，可以根据这个ID查找对应的资源
	 * 
	 * @see org.springframework.security.userdetails.UserDetails#getAuthorities()
	 */
	@Transient
	public GrantedAuthority[] getAuthorities() {
		List<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();
		// 添加用户对对应的RoleId
		for (String userInRoleId : getUserInRoleIds()) {
			grantedAuthorities.add(new GrantedAuthorityImpl(userInRoleId.toString()));
		}
		// 添加用户对对应的组织架构的RoleId
		for (String userInOrgRoleId : getUserInOrgRoleIds()) {
			grantedAuthorities.add(new GrantedAuthorityImpl(userInOrgRoleId.toString()));
		}
		// 默认添加一个操作ACL的权限,以跳过spring security内置的ACL操作检查,本系统的操作检查由URL过滤进行控制。
		grantedAuthorities.add(new GrantedAuthorityImpl("4000"));
		return grantedAuthorities
				.toArray(new GrantedAuthority[getUserInRoleIds().size() + getUserInOrgRoleIds().size()]);
	}

	/**
	 * Returns the authorites string
	 * 
	 * eg. downpour --- ROLE_ADMIN,ROLE_USER robbin --- ROLE_ADMIN
	 * 
	 * @return
	 */
	@Transient
	public String getAuthoritiesString() {
		List<String> authorities = new ArrayList<String>();
		for (GrantedAuthority authority : this.getAuthorities()) {
			authorities.add(authority.getAuthority());
		}
		return StringUtils.join(authorities, ",");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.security.userdetails.UserDetails#getPassword()
	 */
	@Transient
	public String getPassword() {
		return this.getUserPwd();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.security.userdetails.UserDetails#getUsername()
	 */
	@Transient
	public String getUsername() {
		return this.userAccount;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.security.userdetails.UserDetails#isAccountNonExpired()
	 */
	@Transient
	public boolean isAccountNonExpired() {
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.security.userdetails.UserDetails#isAccountNonLocked()
	 */
	@Transient
	public boolean isAccountNonLocked() {
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.security.userdetails.UserDetails#isCredentialsNonExpired()
	 */
	@Transient
	public boolean isCredentialsNonExpired() {
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.security.userdetails.UserDetails#isEnabled()
	 */
	@Transient
	public boolean isEnabled() {
		if ("0".equalsIgnoreCase(this.getIsInvalid())) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * @return the userInRoleIds
	 */
	@Transient
	public List<String> getUserInRoleIds() {
		return userInRoleIds;
	}

	/**
	 * @param userInRoleIds
	 *            the userInRoleIds to set
	 */
	@Transient
	public void setUserInRoleIds(List<String> userInRoleIds) {
		this.userInRoleIds = userInRoleIds;
	}

	/**
	 * @return the userInOrgRoleIds
	 */
	@Transient
	public List<String> getUserInOrgRoleIds() {
		return userInOrgRoleIds;
	}

	/**
	 * @param userInOrgRoleIds
	 *            the userInOrgRoleIds to set
	 */
	@Transient
	public void setUserInOrgRoleIds(List<String> userInOrgRoleIds) {
		this.userInOrgRoleIds = userInOrgRoleIds;
	}

	@Transient
	public int getMask() {
		return permission != null ? permission.getMask() : 0;
	}

	@Transient
	public String getDomainId() {
		return this.getIdNo();
	}

	@Transient
	public Permission getPermission() {
		return permission;
	}

	public void setPermission(Permission permission) {
		this.permission = permission;
	}

	@Transient
	public String getOldPwd() {
		return oldPwd;
	}

	public void setOldPwd(String oldPwd) {
		this.oldPwd = oldPwd;
	}

	@Transient
	public String getNewPwd1() {
		return newPwd1;
	}

	public void setNewPwd1(String newPwd1) {
		this.newPwd1 = newPwd1;
	}

	@Transient
	public String getNewPwd2() {
		return newPwd2;
	}

	public void setNewPwd2(String newPwd2) {
		this.newPwd2 = newPwd2;
	}

	
	@Transient
	public String getRoleIds() {
		return roleIds;
	}

	
	public void setRoleIds(String roleIds) {
		this.roleIds = roleIds;
	}

	@Transient
	public String getRoleNames() {
		return roleNames;
	}

	
	public void setRoleNames(String roleNames) {
		this.roleNames = roleNames;
	}

	@Transient
	public String getIds() {
		return ids;
	}

	
	public void setIds(String ids) {
		this.ids = ids;
	}

	@Transient
	public String getIpAddress() {
		return ipAddress;
	}

	
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	@Transient
	public Double getAchFee() {
		return achFee;
	}

	
	public void setAchFee(Double achFee) {
		this.achFee = achFee;
	}

	@Transient
	public Integer getClientNum() {
		return clientNum;
	}

	
	public void setClientNum(Integer clientNum) {
		this.clientNum = clientNum;
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

	@Transient
	public String getDispatchOther() {
		return dispatchOther;
	}

	
	public void setDispatchOther(String dispatchOther) {
		this.dispatchOther = dispatchOther;
	}

	@Transient
	public String getPayType() {
		return payType;
	}

	
	public void setPayType(String payType) {
		this.payType = payType;
	}

	
}

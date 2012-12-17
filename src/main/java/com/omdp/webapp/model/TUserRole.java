package com.omdp.webapp.model;

import java.util.Date;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * TUserRole entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_user_role")
public class TUserRole implements java.io.Serializable {

	// Fields

	private TUserRoleId id;
	private Date createTime;
	private String oprId;


	// Constructors

	/** default constructor */
	public TUserRole() {
	}

	/** minimal constructor */
	public TUserRole(TUserRoleId id) {
		this.id = id;
	}

	/** full constructor */
	public TUserRole(TUserRoleId id, Date createTime, String oprId) {
		this.id = id;
		this.createTime = createTime;
		this.oprId = oprId;
	}

	// Property accessors
	@EmbeddedId
	@AttributeOverrides( {
			@AttributeOverride(name = "userId", column = @Column(name = "USER_ID", nullable = false, length = 20)),
			@AttributeOverride(name = "roleId", column = @Column(name = "ROLE_ID", nullable = false, length = 16)) })
	public TUserRoleId getId() {
		return this.id;
	}

	public void setId(TUserRoleId id) {
		this.id = id;
	}

	@Temporal(TemporalType.DATE)
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

}

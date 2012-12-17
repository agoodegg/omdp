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
 * TRoleRes entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_role_res")
public class TRoleRes implements java.io.Serializable {

	// Fields

	private TRoleResId id;
	private Date createTime;
	private String oprId;


	// Constructors

	/** default constructor */
	public TRoleRes() {
	}

	/** minimal constructor */
	public TRoleRes(TRoleResId id) {
		this.id = id;
	}

	/** full constructor */
	public TRoleRes(TRoleResId id, Date createTime, String oprId) {
		this.id = id;
		this.createTime = createTime;
		this.oprId = oprId;
	}

	// Property accessors
	@EmbeddedId
	@AttributeOverrides( {
			@AttributeOverride(name = "roleId", column = @Column(name = "ROLE_ID", nullable = false, length = 16)),
			@AttributeOverride(name = "resId", column = @Column(name = "RES_ID", nullable = false, length = 16)) })
	public TRoleResId getId() {
		return this.id;
	}

	public void setId(TRoleResId id) {
		this.id = id;
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

}

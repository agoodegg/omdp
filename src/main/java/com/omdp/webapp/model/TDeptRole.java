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


@Entity
@Table(name = "t_dept_role")
public class TDeptRole implements java.io.Serializable{

	private TDeptRoleId id;
	private Date createTime;
	private String oprId;
	
	public TDeptRole(){
		
	}
	
	public TDeptRole(TDeptRoleId id){
		this.id = id;
	}

	public TDeptRole(TDeptRoleId id, Date createTime, String oprId) {
		super();
		this.id = id;
		this.createTime = createTime;
		this.oprId = oprId;
	}
	
	// Property accessors
	@EmbeddedId
	@AttributeOverrides( {
			@AttributeOverride(name = "deptCd", column = @Column(name = "DEPT_CD", nullable = false, length = 20)),
			@AttributeOverride(name = "roleId", column = @Column(name = "ROLE_ID", nullable = false, length = 16)) })
	public TDeptRoleId getId() {
		return this.id;
	}

	public void setId(TDeptRoleId id) {
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

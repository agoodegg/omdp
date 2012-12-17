package com.omdp.webapp.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TDeptInfo entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_dept_info")
public class TDeptInfo implements java.io.Serializable {

	// Fields

	private Integer id;
	private String deptCd;
	private String deptName;
	private Integer pid;
	private String deptDesc;


	// Constructors

	/** default constructor */
	public TDeptInfo() {
	}

	/** full constructor */
	public TDeptInfo(String deptCd, String deptName, Integer pid, String deptDesc) {
		this.deptCd = deptCd;
		this.deptName = deptName;
		this.pid = pid;
		this.deptDesc = deptDesc;
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

	@Column(name = "DEPT_CD", length = 20)
	public String getDeptCd() {
		return this.deptCd;
	}

	public void setDeptCd(String deptCd) {
		this.deptCd = deptCd;
	}

	@Column(name = "DEPT_NAME", length = 40)
	public String getDeptName() {
		return this.deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	@Column(name = "PID")
	public Integer getPid() {
		return pid;
	}
	
	public void setPid(Integer pid) {
		this.pid = pid;
	}


	@Column(name = "DEPT_DESC", length = 160)
	public String getDeptDesc() {
		return this.deptDesc;
	}

	public void setDeptDesc(String deptDesc) {
		this.deptDesc = deptDesc;
	}

}

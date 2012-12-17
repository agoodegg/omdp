package com.omdp.webapp.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;


@Embeddable
public class TDeptRoleId implements java.io.Serializable{

	private String deptCd;
	private String roleId;
	
	
	public TDeptRoleId(String deptCd, String roleId) {
		super();
		this.deptCd = deptCd;
		this.roleId = roleId;
	}
	
	public TDeptRoleId(){
		
	}

	
	@Column(name = "DEPT_CD", nullable = false, length = 20)
	public String getDeptCd() {
		return deptCd;
	}

	
	public void setDeptCd(String deptCd) {
		this.deptCd = deptCd;
	}

	
	@Column(name = "ROLE_ID", nullable = false, length = 16)
	public String getRoleId() {
		return roleId;
	}

	
	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}
	
	
	
	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof TDeptRoleId))
			return false;
		TDeptRoleId castOther = (TDeptRoleId) other;

		return ((this.getDeptCd() == castOther.getDeptCd()) || (this.getDeptCd() != null
				&& castOther.getDeptCd() != null && this.getDeptCd().equals(castOther.getDeptCd())))
				&& ((this.getRoleId() == castOther.getRoleId()) || (this.getRoleId() != null
						&& castOther.getRoleId() != null && this.getRoleId().equals(castOther.getRoleId())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getDeptCd() == null ? 0 : this.getDeptCd().hashCode());
		result = 37 * result + (getRoleId() == null ? 0 : this.getRoleId().hashCode());
		return result;
	}
	
}

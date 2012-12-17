package com.omdp.webapp.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * TRoleResId entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Embeddable
public class TRoleResId implements java.io.Serializable {

	// Fields

	private String roleId;
	private String resId;


	// Constructors

	/** default constructor */
	public TRoleResId() {
	}

	/** full constructor */
	public TRoleResId(String roleId, String resId) {
		this.roleId = roleId;
		this.resId = resId;
	}

	// Property accessors

	@Column(name = "ROLE_ID", nullable = false, length = 16)
	public String getRoleId() {
		return this.roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	@Column(name = "RES_ID", nullable = false, length = 16)
	public String getResId() {
		return this.resId;
	}

	public void setResId(String resId) {
		this.resId = resId;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof TRoleResId))
			return false;
		TRoleResId castOther = (TRoleResId) other;

		return ((this.getRoleId() == castOther.getRoleId()) || (this.getRoleId() != null
				&& castOther.getRoleId() != null && this.getRoleId().equals(castOther.getRoleId())))
				&& ((this.getResId() == castOther.getResId()) || (this.getResId() != null
						&& castOther.getResId() != null && this.getResId().equals(castOther.getResId())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (getRoleId() == null ? 0 : this.getRoleId().hashCode());
		result = 37 * result + (getResId() == null ? 0 : this.getResId().hashCode());
		return result;
	}

}

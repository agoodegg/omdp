package com.omdp.webapp.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TBusiInfo entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_busi_info")
public class TBusiInfo implements java.io.Serializable {

	// Fields

	private Integer id;
	private String busiName;
	private String busiCd;
	private String busiMemo;


	// Constructors

	/** default constructor */
	public TBusiInfo() {
	}

	/** minimal constructor */
	public TBusiInfo(String busiName, String busiCd) {
		this.busiName = busiName;
		this.busiCd = busiCd;
	}

	/** full constructor */
	public TBusiInfo(String busiName, String busiCd, String busiMemo) {
		this.busiName = busiName;
		this.busiCd = busiCd;
		this.busiMemo = busiMemo;
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

	@Column(name = "BUSI_NAME", nullable = false, length = 40)
	public String getBusiName() {
		return this.busiName;
	}

	public void setBusiName(String busiName) {
		this.busiName = busiName;
	}

	@Column(name = "BUSI_CD", nullable = false, length = 20)
	public String getBusiCd() {
		return this.busiCd;
	}

	public void setBusiCd(String busiCd) {
		this.busiCd = busiCd;
	}

	@Column(name = "BUSI_MEMO", length = 100)
	public String getBusiMemo() {
		return this.busiMemo;
	}

	public void setBusiMemo(String busiMemo) {
		this.busiMemo = busiMemo;
	}

}

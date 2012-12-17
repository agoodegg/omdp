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
 * TOrderLog entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_order_log")
public class TOrderLog implements java.io.Serializable {

	// Fields

	private Integer id;
	private String orderId;
	private String oprId;
	private String oprName;
	private Date oprTime;
	private String oprDesc;
	private String oprMemo;


	// Constructors

	/** default constructor */
	public TOrderLog() {
	}

	/** full constructor */
	public TOrderLog(String orderId, String oprId, String oprName, Date oprTime, String oprDesc, String oprMemo) {
		this.orderId = orderId;
		this.oprId = oprId;
		this.oprName = oprName;
		this.oprTime = oprTime;
		this.oprDesc = oprDesc;
		this.oprMemo = oprMemo;
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

	@Column(name = "ORDER_ID", length = 20)
	public String getOrderId() {
		return this.orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	@Column(name = "OPR_ID", length = 20)
	public String getOprId() {
		return this.oprId;
	}

	public void setOprId(String oprId) {
		this.oprId = oprId;
	}

	@Column(name = "OPR_NAME", length = 40)
	public String getOprName() {
		return this.oprName;
	}

	public void setOprName(String oprName) {
		this.oprName = oprName;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "OPR_TIME", length = 19)
	public Date getOprTime() {
		return this.oprTime;
	}

	public void setOprTime(Date oprTime) {
		this.oprTime = oprTime;
	}

	@Column(name = "OPR_DESC", length = 200)
	public String getOprDesc() {
		return this.oprDesc;
	}

	public void setOprDesc(String oprDesc) {
		this.oprDesc = oprDesc;
	}

	@Column(name = "OPR_MEMO", length = 200)
	public String getOprMemo() {
		return this.oprMemo;
	}

	public void setOprMemo(String oprMemo) {
		this.oprMemo = oprMemo;
	}

}

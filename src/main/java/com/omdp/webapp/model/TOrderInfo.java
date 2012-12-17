package com.omdp.webapp.model;

import static javax.persistence.GenerationType.IDENTITY;

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

/**
 * TOrderInfo entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_order_info")
public class TOrderInfo implements java.io.Serializable {

	// Fields

	private Integer id;
	private String ordernum;
	private String orderTitle;
	private String filepos;
	private String fileSrc;
	private String software;
	private String custId;
	private String custName;
	private Date orderTime;
	private Date doneTime;
	private Date deliverTime;
	private String deliverMethod;
	private String deliverAddress;
	private String printMemo;
	private String typesetOpr;
	private String bindingOpr;
	private String linkMan;
	private String tel;
	private String device;
	private String orderMemo;
	private String seeOrderOpr;
	private String creatorName;
	private String creator;
	private Double price;
	private String payType;
	private String payStatus;
	private String orderStatus;      //00000   从左至右 依次代表是否打印、是否完工、是否作废、是否付款(结算)、是否已核销
	private String prepayFlag;
	private Double prepay;
	private String prepayMemo;
	private String dispatchOther;
	private String dispatchName;
	private String agentAddress;
	private String agentMobile;
	private String trashMemo;
	private String trashOprName;
	private String col1;
	private String col2;
	private String col3;
	private String col4;
	private String col5;
	private String col6;
	private String col7;
	private String ext1;    //是否为月结单    扩展字段    2011-05-07 by zhouxiaohui   0非月结  1为月结单    默认为0月结    兼容已有数据为空的情况
	private String ext2;
	private String ext3;
	private String ext4;
	


	private String orderTimeStr;
	private String doneTimeStr;
	private String deliverTimeStr;
	private int hour;
	private String orderTimePickerU;
	private String orderTimePickerD;
	
	private String bindOprNames;
	private String typeOprNames;
	
	private String tradStatus;
	
	private Integer totalOrderCount;
	private Double totalFee;
	
	private String orderStatusCn;
	
	private List<TOrderDetail> orderDetails;
	// Constructors

	/** default constructor */
	public TOrderInfo() {
	}

	/** minimal constructor */
	public TOrderInfo(String ordernum,  Date deliverTime) {
		this.ordernum = ordernum;
		this.deliverTime = deliverTime;
	}

	/** full constructor */
	public TOrderInfo(String ordernum,String orderTitle, String filepos, String fileSrc, String software, String custId, 
			String custName, Date orderTime, Date doneTime, Date deliverTime, String deliverMethod,
			String deliverAddress, String printMemo, String typesetOpr, String bindingOpr,String linkMan, String tel, String device,
			String orderMemo, String seeOrderOpr, String creatorName, String creator, Double price, String payStatus, String payType, String orderStatus, String prepayFlag,
			Double prepay, String prepayMemo,String dispatchOther, String dispatchName, String trashMemo, String trashOprName, String col1, String col2, String col3, String col4, String col5,
			String col6, String col7, String ext1, String ext2, String ext3, String ext4) {
		this.ordernum = ordernum;
		this.filepos = filepos;
		this.fileSrc = fileSrc;
		this.software = software;
		this.custId = custId;
		this.custName = custName;
		this.orderTime = orderTime;
		this.doneTime = doneTime;
		this.deliverTime = deliverTime;
		this.deliverMethod = deliverMethod;
		this.deliverAddress = deliverAddress;
		this.printMemo = printMemo;
		this.typesetOpr = typesetOpr;
		this.bindingOpr = bindingOpr;
		this.linkMan = linkMan;
		this.tel = tel;
		this.device = device;
		this.orderMemo = orderMemo;
		this.seeOrderOpr = seeOrderOpr;
		this.creatorName = creatorName;
		this.creator = creator;
		this.price = price;
		this.payType = payType;
		this.payStatus = payStatus;
		this.orderStatus = orderStatus;
		this.prepayFlag = prepayFlag;
		this.prepay = prepay;
		this.prepayMemo = prepayMemo;
		this.dispatchOther = dispatchOther;
		this.dispatchName = dispatchName;
		this.trashMemo = trashMemo;
		this.trashOprName = trashOprName;
		this.col1 = col1;
		this.col2 = col2;
		this.col3 = col3;
		this.col4 = col4;
		this.col5 = col5;
		this.col6 = col6;
		this.col7 = col7;
		this.ext1 = ext1;
		this.ext2 = ext2;
		this.ext3 = ext3;
		this.ext4 = ext4;
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

	@Column(name = "ORDERNUM", nullable = false, length = 20)
	public String getOrdernum() {
		return this.ordernum;
	}

	public void setOrdernum(String ordernum) {
		this.ordernum = ordernum;
	}
	
	@Column(name = "ORDER_TITLE", nullable = false, length = 200)
	public String getOrderTitle() {
		return orderTitle;
	}

	
	public void setOrderTitle(String orderTitle) {
		this.orderTitle = orderTitle;
	}

	@Column(name = "FILEPOS", length = 128)
	public String getFilepos() {
		return this.filepos;
	}

	public void setFilepos(String filepos) {
		this.filepos = filepos;
	}

	@Column(name = "FILE_SRC", length = 40)
	public String getFileSrc() {
		return this.fileSrc;
	}

	public void setFileSrc(String fileSrc) {
		this.fileSrc = fileSrc;
	}

	@Column(name = "SOFTWARE", length = 100)
	public String getSoftware() {
		return this.software;
	}

	public void setSoftware(String software) {
		this.software = software;
	}

	@Column(name = "CUST_ID", length = 20)
	public String getCustId() {
		return this.custId;
	}

	public void setCustId(String custId) {
		this.custId = custId;
	}

	@Column(name = "CUST_NAME", length = 40)
	public String getCustName() {
		return this.custName;
	}

	public void setCustName(String custName) {
		this.custName = custName;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "ORDER_TIME", length = 19)
	public Date getOrderTime() {
		return this.orderTime;
	}

	public void setOrderTime(Date orderTime) {
		this.orderTime = orderTime;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DONE_TIME", length = 19)
	public Date getDoneTime() {
		return this.doneTime;
	}

	public void setDoneTime(Date doneTime) {
		this.doneTime = doneTime;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DELIVER_TIME", nullable = false, length = 19)
	public Date getDeliverTime() {
		return this.deliverTime;
	}

	public void setDeliverTime(Date deliverTime) {
		this.deliverTime = deliverTime;
	}

	@Column(name = "DELIVER_METHOD", length = 20)
	public String getDeliverMethod() {
		return this.deliverMethod;
	}

	public void setDeliverMethod(String deliverMethod) {
		this.deliverMethod = deliverMethod;
	}

	@Column(name = "DELIVER_ADDRESS", length = 100)
	public String getDeliverAddress() {
		return this.deliverAddress;
	}

	public void setDeliverAddress(String deliverAddress) {
		this.deliverAddress = deliverAddress;
	}

	@Column(name = "PRINT_MEMO", length = 200)
	public String getPrintMemo() {
		return this.printMemo;
	}

	public void setPrintMemo(String printMemo) {
		this.printMemo = printMemo;
	}

	@Column(name = "TYPESET_OPR", length = 80)
	public String getTypesetOpr() {
		return this.typesetOpr;
	}

	public void setTypesetOpr(String typesetOpr) {
		this.typesetOpr = typesetOpr;
	}

	@Column(name = "BINDING_OPR", length = 80)
	public String getBindingOpr() {
		return this.bindingOpr;
	}

	public void setBindingOpr(String bindingOpr) {
		this.bindingOpr = bindingOpr;
	}
	
	@Column(name = "LINK_MAN", length = 80)
	public String getLinkMan() {
		return linkMan;
	}

	
	public void setLinkMan(String linkMan) {
		this.linkMan = linkMan;
	}

	@Column(name = "TEL", length = 40)
	public String getTel() {
		return this.tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	@Column(name = "DEVICE", length = 100)
	public String getDevice() {
		return this.device;
	}

	public void setDevice(String device) {
		this.device = device;
	}

	@Column(name = "ORDER_MEMO", length = 200)
	public String getOrderMemo() {
		return this.orderMemo;
	}

	public void setOrderMemo(String orderMemo) {
		this.orderMemo = orderMemo;
	}
	

	@Column(name = "SEE_ORDER_OPR", length = 20)
	public String getSeeOrderOpr() {
		return seeOrderOpr;
	}

	
	public void setSeeOrderOpr(String seeOrderOpr) {
		this.seeOrderOpr = seeOrderOpr;
	}
	
	@Column(name = "CREATOR_NAME", length = 40)
	public String getCreatorName() {
		return creatorName;
	}

	
	public void setCreatorName(String creatorName) {
		this.creatorName = creatorName;
	}

	@Column(name = "CREATOR", length = 20)
	public String getCreator() {
		return this.creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	@Column(name = "PRICE", precision = 12)
	public Double getPrice() {
		return this.price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}
	
	@Column(name = "PAY_TYPE", length = 20)
	public String getPayType() {
		return payType;
	}

	
	public void setPayType(String payType) {
		this.payType = payType;
	}

	@Column(name = "PAY_STATUS", length = 10)
	public String getPayStatus() {
		return this.payStatus;
	}

	public void setPayStatus(String payStatus) {
		this.payStatus = payStatus;
	}

	@Column(name = "ORDER_STATUS", length = 10)
	public String getOrderStatus() {
		return this.orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	@Column(name = "PREPAY_FLAG", length = 10)
	public String getPrepayFlag() {
		return this.prepayFlag;
	}

	public void setPrepayFlag(String prepayFlag) {
		this.prepayFlag = prepayFlag;
	}

	@Column(name = "PREPAY", precision = 12)
	public Double getPrepay() {
		return this.prepay;
	}

	public void setPrepay(Double prepay) {
		this.prepay = prepay;
	}

	@Column(name = "PREPAY_MEMO", length = 200)
	public String getPrepayMemo() {
		return this.prepayMemo;
	}

	public void setPrepayMemo(String prepayMemo) {
		this.prepayMemo = prepayMemo;
	}

	@Column(name = "col1", length = 20)
	public String getCol1() {
		return this.col1;
	}

	public void setCol1(String col1) {
		this.col1 = col1;
	}

	@Column(name = "col2", length = 20)
	public String getCol2() {
		return this.col2;
	}

	public void setCol2(String col2) {
		this.col2 = col2;
	}

	@Column(name = "col3", length = 20)
	public String getCol3() {
		return this.col3;
	}

	public void setCol3(String col3) {
		this.col3 = col3;
	}

	@Column(name = "col4", length = 20)
	public String getCol4() {
		return this.col4;
	}

	public void setCol4(String col4) {
		this.col4 = col4;
	}

	@Column(name = "col5", length = 20)
	public String getCol5() {
		return this.col5;
	}

	public void setCol5(String col5) {
		this.col5 = col5;
	}

	@Column(name = "col6", length = 20)
	public String getCol6() {
		return this.col6;
	}

	public void setCol6(String col6) {
		this.col6 = col6;
	}

	@Column(name = "col7", length = 20)
	public String getCol7() {
		return this.col7;
	}

	public void setCol7(String col7) {
		this.col7 = col7;
	}

	@Column(name = "ext1", length = 20)
	public String getExt1() {
		return this.ext1;
	}

	public void setExt1(String ext1) {
		this.ext1 = ext1;
	}

	@Column(name = "ext2", length = 20)
	public String getExt2() {
		return this.ext2;
	}

	public void setExt2(String ext2) {
		this.ext2 = ext2;
	}

	@Column(name = "ext3", length = 20)
	public String getExt3() {
		return this.ext3;
	}

	public void setExt3(String ext3) {
		this.ext3 = ext3;
	}

	@Column(name = "ext4", length = 20)
	public String getExt4() {
		return this.ext4;
	}

	public void setExt4(String ext4) {
		this.ext4 = ext4;
	}
	
	
	@Column(name = "DISPATCH_OTHER", length = 10)
	public String getDispatchOther() {
		return dispatchOther;
	}

	
	public void setDispatchOther(String dispatchOther) {
		this.dispatchOther = dispatchOther;
	}

	@Column(name = "DISPATCH_NAME", length = 80)
	public String getDispatchName() {
		return dispatchName;
	}

	
	public void setDispatchName(String dispatchName) {
		this.dispatchName = dispatchName;
	}
	
	@Column(name = "AGENT_ADDRESS", length = 100)
	public String getAgentAddress() {
		return agentAddress;
	}

	
	public void setAgentAddress(String agentAddress) {
		this.agentAddress = agentAddress;
	}

	@Column(name = "AGENT_MOBILE", length = 20)
	public String getAgentMobile() {
		return agentMobile;
	}

	
	public void setAgentMobile(String agentMobile) {
		this.agentMobile = agentMobile;
	}
	
	@Column(name = "TRASH_MEMO", length = 200)
	public String getTrashMemo() {
		return trashMemo;
	}

	public void setTrashMemo(String trashMemo) {
		this.trashMemo = trashMemo;
	}
	
	
	@Column(name = "TRASH_OPR_NAME", length = 200)
	public String getTrashOprName() {
		return trashOprName;
	}

	public void setTrashOprName(String trashOprName) {
		this.trashOprName = trashOprName;
	}

	@Transient
	public String getOrderTimeStr() {
		return orderTimeStr;
	}

	
	public void setOrderTimeStr(String orderTimeStr) {
		this.orderTimeStr = orderTimeStr;
	}

	@Transient
	public String getDoneTimeStr() {
		return doneTimeStr;
	}

	
	public void setDoneTimeStr(String doneTimeStr) {
		this.doneTimeStr = doneTimeStr;
	}

	@Transient
	public String getDeliverTimeStr() {
		return deliverTimeStr;
	}

	
	public void setDeliverTimeStr(String deliverTimeStr) {
		this.deliverTimeStr = deliverTimeStr;
	}

	@Transient
	public int getHour() {
		return hour;
	}

	
	public void setHour(int hour) {
		this.hour = hour;
	}

	
	@Transient
	public String getOrderTimePickerU() {
		return orderTimePickerU;
	}

	
	public void setOrderTimePickerU(String orderTimePickerU) {
		this.orderTimePickerU = orderTimePickerU;
	}

	@Transient
	public String getOrderTimePickerD() {
		return orderTimePickerD;
	}

	
	public void setOrderTimePickerD(String orderTimePickerD) {
		this.orderTimePickerD = orderTimePickerD;
	}

	@Transient
	public String getBindOprNames() {
		return bindOprNames;
	}

	
	public void setBindOprNames(String bindOprNames) {
		this.bindOprNames = bindOprNames;
	}

	@Transient
	public String getTypeOprNames() {
		return typeOprNames;
	}

	
	public void setTypeOprNames(String typeOprNames) {
		this.typeOprNames = typeOprNames;
	}

	@Transient
	public String getTradStatus() {
		return tradStatus;
	}

	
	public void setTradStatus(String tradStatus) {
		this.tradStatus = tradStatus;
	}

	@Transient
	public Integer getTotalOrderCount() {
		return totalOrderCount;
	}

	
	public void setTotalOrderCount(Integer totalOrderCount) {
		this.totalOrderCount = totalOrderCount;
	}

	@Transient
	public Double getTotalFee() {
		return totalFee;
	}

	
	public void setTotalFee(Double totalFee) {
		this.totalFee = totalFee;
	}

	@Transient
	public List<TOrderDetail> getOrderDetails() {
		return orderDetails;
	}

	
	public void setOrderDetails(List<TOrderDetail> orderDetails) {
		this.orderDetails = orderDetails;
	}

	@Transient
	public String getOrderStatusCn() {
		return orderStatusCn;
	}

	
	public void setOrderStatusCn(String orderStatusCn) {
		this.orderStatusCn = orderStatusCn;
	}

}

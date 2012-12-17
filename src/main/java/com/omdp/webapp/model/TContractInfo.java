package com.omdp.webapp.model;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

/**
 * TContractInfo entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_contract_info")
public class TContractInfo implements java.io.Serializable {

	// Fields

	private Integer id;
	private String contractNo;
	private String buyer;
	private String orderNum;
	private String custCd;
	private String seller;
	private String package_;
	private String deliverPlace;
	private String payChannel;
	private String memo;
	private Double gross;
	private String buyerAddress;
	private String buyerTel;
	private String buyerFax;
	private String buyerAccountBank;
	private String buyerAccountNo;
	private String buyerMan;
	private Date buyerDate;
	private String sellerAddress;
	private String sellerTel;
	private String sellerFax;
	private String sellerAccountBank;
	private String sellerAccountNo;
	private String sellerMan;
	private Date sellerDate;
	private String status;
	private String creator;
	private Date createTime;
	private Date updateTime;

	@Transient
	private String itemData;
	@Transient
	private String buyerDateStr;
	@Transient
	private String sellerDateStr;

	// Constructors

	/** default constructor */
	public TContractInfo() {
	}

	/** minimal constructor */
	public TContractInfo(String contractNo) {
		this.contractNo = contractNo;
	}

	/** full constructor */
	public TContractInfo(String contractNo, String buyer, String orderNum, String custCd, String seller,
			String package_, String deliverPlace, String payChannel, String memo, Double gross, String buyerAddress,
			String buyerTel, String buyerFax, String buyerAccountBank, String buyerAccountNo, String buyerMan,
			Date buyerDate, String sellerAddress, String sellerTel, String sellerFax, String sellerAccountBank,
			String sellerAccountNo, String sellerMan, Date sellerDate, String status, String creator, Date createTime, Date updateTime) {
		this.contractNo = contractNo;
		this.buyer = buyer;
		this.orderNum = orderNum;
		this.custCd = custCd;
		this.seller = seller;
		this.package_ = package_;
		this.deliverPlace = deliverPlace;
		this.payChannel = payChannel;
		this.memo = memo;
		this.gross = gross;
		this.buyerAddress = buyerAddress;
		this.buyerTel = buyerTel;
		this.buyerFax = buyerFax;
		this.buyerAccountBank = buyerAccountBank;
		this.buyerAccountNo = buyerAccountNo;
		this.buyerMan = buyerMan;
		this.buyerDate = buyerDate;
		this.sellerAddress = sellerAddress;
		this.sellerTel = sellerTel;
		this.sellerFax = sellerFax;
		this.sellerAccountBank = sellerAccountBank;
		this.sellerAccountNo = sellerAccountNo;
		this.sellerMan = sellerMan;
		this.sellerDate = sellerDate;
		this.status = status;
		this.creator = creator;
		this.createTime = createTime;
		this.updateTime = updateTime;
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

	@Column(name = "CONTRACT_NO", length = 20, nullable = false)
	public String getContractNo() {
		return this.contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	@Column(name = "BUYER", length = 80)
	public String getBuyer() {
		return this.buyer;
	}

	public void setBuyer(String buyer) {
		this.buyer = buyer;
	}

	@Column(name = "ORDER_NUM", length = 20)
	public String getOrderNum() {
		return this.orderNum;
	}

	public void setOrderNum(String orderNum) {
		this.orderNum = orderNum;
	}

	@Column(name = "CUST_CD", length = 20)
	public String getCustCd() {
		return this.custCd;
	}

	public void setCustCd(String custCd) {
		this.custCd = custCd;
	}

	@Column(name = "SELLER", length = 80)
	public String getSeller() {
		return this.seller;
	}

	public void setSeller(String seller) {
		this.seller = seller;
	}

	@Column(name = "PACKAGE", length = 200)
	public String getPackage_() {
		return this.package_;
	}

	public void setPackage_(String package_) {
		this.package_ = package_;
	}

	@Column(name = "DELIVER_PLACE", length = 120)
	public String getDeliverPlace() {
		return this.deliverPlace;
	}

	public void setDeliverPlace(String deliverPlace) {
		this.deliverPlace = deliverPlace;
	}

	@Column(name = "PAY_CHANNEL", length = 100)
	public String getPayChannel() {
		return this.payChannel;
	}

	public void setPayChannel(String payChannel) {
		this.payChannel = payChannel;
	}

	@Column(name = "MEMO", length = 240)
	public String getMemo() {
		return this.memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	@Column(name = "GROSS", precision = 12)
	public Double getGross() {
		return this.gross;
	}

	public void setGross(Double gross) {
		this.gross = gross;
	}

	@Column(name = "BUYER_ADDRESS", length = 120)
	public String getBuyerAddress() {
		return this.buyerAddress;
	}

	public void setBuyerAddress(String buyerAddress) {
		this.buyerAddress = buyerAddress;
	}

	@Column(name = "BUYER_TEL", length = 40)
	public String getBuyerTel() {
		return this.buyerTel;
	}

	public void setBuyerTel(String buyerTel) {
		this.buyerTel = buyerTel;
	}

	@Column(name = "BUYER_FAX", length = 40)
	public String getBuyerFax() {
		return this.buyerFax;
	}

	public void setBuyerFax(String buyerFax) {
		this.buyerFax = buyerFax;
	}

	@Column(name = "BUYER_ACCOUNT_BANK", length = 60)
	public String getBuyerAccountBank() {
		return this.buyerAccountBank;
	}

	public void setBuyerAccountBank(String buyerAccountBank) {
		this.buyerAccountBank = buyerAccountBank;
	}

	@Column(name = "BUYER_ACCOUNT_NO", length = 40)
	public String getBuyerAccountNo() {
		return this.buyerAccountNo;
	}

	public void setBuyerAccountNo(String buyerAccountNo) {
		this.buyerAccountNo = buyerAccountNo;
	}

	@Column(name = "BUYER_MAN", length = 40)
	public String getBuyerMan() {
		return this.buyerMan;
	}

	public void setBuyerMan(String buyerMan) {
		this.buyerMan = buyerMan;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "BUYER_DATE", length = 19)
	public Date getBuyerDate() {
		return this.buyerDate;
	}

	public void setBuyerDate(Date buyerDate) {
		this.buyerDate = buyerDate;
	}

	@Column(name = "SELLER_ADDRESS", length = 120)
	public String getSellerAddress() {
		return this.sellerAddress;
	}

	public void setSellerAddress(String sellerAddress) {
		this.sellerAddress = sellerAddress;
	}

	@Column(name = "SELLER_TEL", length = 40)
	public String getSellerTel() {
		return this.sellerTel;
	}

	public void setSellerTel(String sellerTel) {
		this.sellerTel = sellerTel;
	}

	@Column(name = "SELLER_FAX", length = 40)
	public String getSellerFax() {
		return this.sellerFax;
	}

	public void setSellerFax(String sellerFax) {
		this.sellerFax = sellerFax;
	}

	@Column(name = "SELLER_ACCOUNT_BANK", length = 60)
	public String getSellerAccountBank() {
		return this.sellerAccountBank;
	}

	public void setSellerAccountBank(String sellerAccountBank) {
		this.sellerAccountBank = sellerAccountBank;
	}

	@Column(name = "SELLER_ACCOUNT_NO", length = 40)
	public String getSellerAccountNo() {
		return this.sellerAccountNo;
	}

	public void setSellerAccountNo(String sellerAccountNo) {
		this.sellerAccountNo = sellerAccountNo;
	}

	@Column(name = "SELLER_MAN", length = 40)
	public String getSellerMan() {
		return this.sellerMan;
	}

	public void setSellerMan(String sellerMan) {
		this.sellerMan = sellerMan;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "SELLER_DATE", length = 19)
	public Date getSellerDate() {
		return this.sellerDate;
	}

	public void setSellerDate(Date sellerDate) {
		this.sellerDate = sellerDate;
	}

	@Column(name = "STATUS", length = 20)
	public String getStatus() {
		return status;
	}

	
	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name = "CREATOR", length = 40)
	public String getCreator() {
		return this.creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATE_TIME", length = 19)
	public Date getCreateTime() {
		return this.createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "UPDATE_TIME", length = 19)
	public Date getUpdateTime() {
		return this.updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	
	@Transient
	public String getItemData() {
		return itemData;
	}

	
	public void setItemData(String itemData) {
		this.itemData = itemData;
	}

	@Transient
	public String getBuyerDateStr() {
		return buyerDateStr;
	}

	
	public void setBuyerDateStr(String buyerDateStr) {
		this.buyerDateStr = buyerDateStr;
	}

	@Transient
	public String getSellerDateStr() {
		return sellerDateStr;
	}

	
	public void setSellerDateStr(String sellerDateStr) {
		this.sellerDateStr = sellerDateStr;
	}

}

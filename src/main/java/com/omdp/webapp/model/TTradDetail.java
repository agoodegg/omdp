package com.omdp.webapp.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TTradDetail entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_trad_detail")
public class TTradDetail implements java.io.Serializable {

	// Fields

	private Integer id;
	private String tradCd;
	private String ordernum;
	private String itemType;
	private String itemName;
	private String itemCode;
	private String filePath;
	private Double amount;
	private String expression;
	private Double unitPrice;
	private Double total;
	private String unit;
	private String request;
	private String esp1;
	private String code1;
	private String esp2;
	private String code2;
	private String esp3;
	private String code3;
	private String esp4;
	private String code4;
	private Float complete;
	private String completeExp;


	// Constructors

	/** default constructor */
	public TTradDetail() {
	}

	/** minimal constructor */
	public TTradDetail(String tradCd, String ordernum, String itemType, String itemName, Double amount, Double total,
			String unit) {
		this.tradCd = tradCd;
		this.ordernum = ordernum;
		this.itemType = itemType;
		this.itemName = itemName;
		this.amount = amount;
		this.total = total;
		this.unit = unit;
	}

	/** full constructor */
	public TTradDetail(String tradCd, String ordernum, String itemType, String itemName, String itemCode,
			String filePath, Double amount, String expression, Double unitPrice, Double total, String unit,
			String request, String esp1, String code1, String esp2, String code2, String esp3, String code3,
			String esp4, String code4, Float complete, String completeExp) {
		this.tradCd = tradCd;
		this.ordernum = ordernum;
		this.itemType = itemType;
		this.itemName = itemName;
		this.itemCode = itemCode;
		this.filePath = filePath;
		this.amount = amount;
		this.expression = expression;
		this.unitPrice = unitPrice;
		this.total = total;
		this.unit = unit;
		this.request = request;
		this.esp1 = esp1;
		this.code1 = code1;
		this.esp2 = esp2;
		this.code2 = code2;
		this.esp3 = esp3;
		this.code3 = code3;
		this.esp4 = esp4;
		this.code4 = code4;
		this.complete = complete;
		this.completeExp = completeExp;
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

	@Column(name = "TRAD_CD", nullable = false, length = 20)
	public String getTradCd() {
		return this.tradCd;
	}

	public void setTradCd(String tradCd) {
		this.tradCd = tradCd;
	}

	@Column(name = "ORDERNUM", nullable = false, length = 80)
	public String getOrdernum() {
		return this.ordernum;
	}

	public void setOrdernum(String ordernum) {
		this.ordernum = ordernum;
	}

	@Column(name = "ITEM_TYPE", nullable = false, length = 20)
	public String getItemType() {
		return this.itemType;
	}

	public void setItemType(String itemType) {
		this.itemType = itemType;
	}

	@Column(name = "ITEM_NAME", nullable = false, length = 120)
	public String getItemName() {
		return this.itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	@Column(name = "ITEM_CODE", length = 20)
	public String getItemCode() {
		return this.itemCode;
	}

	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}

	@Column(name = "FILE_PATH", length = 200)
	public String getFilePath() {
		return this.filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	@Column(name = "AMOUNT", nullable = false, precision = 12)
	public Double getAmount() {
		return this.amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	@Column(name = "EXPRESSION", length = 40)
	public String getExpression() {
		return this.expression;
	}

	public void setExpression(String expression) {
		this.expression = expression;
	}

	@Column(name = "UNIT_PRICE", precision = 12)
	public Double getUnitPrice() {
		return this.unitPrice;
	}

	public void setUnitPrice(Double unitPrice) {
		this.unitPrice = unitPrice;
	}

	@Column(name = "TOTAL", nullable = false, precision = 12)
	public Double getTotal() {
		return this.total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

	@Column(name = "UNIT", nullable = false, length = 10)
	public String getUnit() {
		return this.unit;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	@Column(name = "request", length = 65535)
	public String getRequest() {
		return this.request;
	}

	public void setRequest(String request) {
		this.request = request;
	}

	@Column(name = "esp1", length = 40)
	public String getEsp1() {
		return this.esp1;
	}

	public void setEsp1(String esp1) {
		this.esp1 = esp1;
	}

	@Column(name = "code1", length = 40)
	public String getCode1() {
		return this.code1;
	}

	public void setCode1(String code1) {
		this.code1 = code1;
	}

	@Column(name = "esp2", length = 40)
	public String getEsp2() {
		return this.esp2;
	}

	public void setEsp2(String esp2) {
		this.esp2 = esp2;
	}

	@Column(name = "code2", length = 40)
	public String getCode2() {
		return this.code2;
	}

	public void setCode2(String code2) {
		this.code2 = code2;
	}

	@Column(name = "esp3", length = 40)
	public String getEsp3() {
		return this.esp3;
	}

	public void setEsp3(String esp3) {
		this.esp3 = esp3;
	}

	@Column(name = "code3", length = 40)
	public String getCode3() {
		return this.code3;
	}

	public void setCode3(String code3) {
		this.code3 = code3;
	}

	@Column(name = "esp4", length = 40)
	public String getEsp4() {
		return this.esp4;
	}

	public void setEsp4(String esp4) {
		this.esp4 = esp4;
	}

	@Column(name = "code4", length = 40)
	public String getCode4() {
		return this.code4;
	}

	public void setCode4(String code4) {
		this.code4 = code4;
	}

	@Column(name = "complete", precision = 12, scale = 0)
	public Float getComplete() {
		return this.complete;
	}

	public void setComplete(Float complete) {
		this.complete = complete;
	}

	@Column(name = "complete_exp", length = 20)
	public String getCompleteExp() {
		return this.completeExp;
	}

	public void setCompleteExp(String completeExp) {
		this.completeExp = completeExp;
	}

}

package com.omdp.webapp.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import static javax.persistence.GenerationType.IDENTITY;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * TContractItem entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "t_contract_item")
public class TContractItem implements java.io.Serializable {

	// Fields

	private Integer id;
	private String contractNo;
	private String itemName;
	private Double mount;
	private String mountUnit;
	private String spec;
	private String mertierial;
	private String requirement;
	private Double price;
	private Double total;


	// Constructors

	/** default constructor */
	public TContractItem() {
	}

	/** full constructor */
	public TContractItem(String contractNo, String itemName, Double mount, String mountUnit, String spec, String mertierial,
			String requirement, Double price, Double total) {
		this.contractNo = contractNo;
		this.itemName = itemName;
		this.mount = mount;
		this.mountUnit = mountUnit;
		this.spec = spec;
		this.mertierial = mertierial;
		this.requirement = requirement;
		this.price = price;
		this.total = total;
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

	@Column(name = "CONTRACT_NO", length = 20)
	public String getContractNo() {
		return this.contractNo;
	}

	public void setContractNo(String contractNo) {
		this.contractNo = contractNo;
	}

	@Column(name = "ITEM_NAME", length = 80)
	public String getItemName() {
		return this.itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	@Column(name = "MOUNT")
	public Double getMount() {
		return this.mount;
	}

	public void setMount(Double mount) {
		this.mount = mount;
	}
	
	@Column(name = "MOUNT_UNIT")
	public String getMountUnit() {
		return mountUnit;
	}

	
	public void setMountUnit(String mountUnit) {
		this.mountUnit = mountUnit;
	}

	@Column(name = "SPEC", length = 120)
	public String getSpec() {
		return this.spec;
	}

	public void setSpec(String spec) {
		this.spec = spec;
	}

	@Column(name = "MERTIERIAL", length = 120)
	public String getMertierial() {
		return this.mertierial;
	}

	public void setMertierial(String mertierial) {
		this.mertierial = mertierial;
	}

	@Column(name = "REQUIREMENT", length = 160)
	public String getRequirement() {
		return this.requirement;
	}

	public void setRequirement(String requirement) {
		this.requirement = requirement;
	}

	@Column(name = "PRICE", precision = 12)
	public Double getPrice() {
		return this.price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	@Column(name = "TOTAL", precision = 12)
	public Double getTotal() {
		return this.total;
	}

	public void setTotal(Double total) {
		this.total = total;
	}

}

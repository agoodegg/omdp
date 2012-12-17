package com.omdp.webapp.contract.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.transaction.annotation.Transactional;

import com.omdp.webapp.base.common.model.Page;
import com.omdp.webapp.contract.dao.IContractDao;
import com.omdp.webapp.contract.service.IContractService;
import com.omdp.webapp.model.TContractInfo;
import com.omdp.webapp.model.TContractItem;
import com.omdp.webapp.model.TUser;


@Transactional
public class ContractServiceImpl implements IContractService {

	@Resource
	private IContractDao<TContractInfo> contractDAO;

	public List<TContractInfo> queryContractInfo(TContractInfo info, TUser u, Page page) {
		return contractDAO.queryContractInfo(info,u,page);
	}

	public void trashContract(TContractInfo info, TUser u) {
		contractDAO.trashContract(info.getContractNo(),u);
	}

	
	public void addNewContract(TContractInfo info, List<TContractItem> items, TUser u) {
		
		contractDAO.save(info);
		
		if(items!=null){
			for(TContractItem item:items){
				item.setContractNo(info.getContractNo());
				contractDAO.saveDetail(item);
			}
		}
		
	}

	public void updateContractTotal(String contractNo) {
		
		contractDAO.updateContractTotal(contractNo);
	}

	public TContractInfo queryContractInfoById(Integer id, TUser u) {
		return contractDAO.queryContractById(id);
	}

	public List<TContractItem> queryContractItem(String contractNo, TUser u) {
		return contractDAO.queryContractItemByContractNo(contractNo,u );
	}

	
	public void editContract(TContractInfo info, List<TContractItem> items, TUser u) {
		
		TContractInfo po = contractDAO.queryContractById(info.getId());
		
		po.setUpdateTime(new Date());
		po.setBuyer(info.getBuyer());
		po.setBuyerAccountBank(info.getBuyerAccountBank());
		po.setBuyerAccountNo(info.getBuyerAccountNo());
		po.setBuyerAddress(info.getBuyerAddress());
		po.setBuyerFax(info.getBuyerFax());
		po.setBuyerMan(info.getBuyerMan());
		po.setBuyerTel(info.getBuyerTel());
		po.setDeliverPlace(info.getDeliverPlace());
		po.setMemo(info.getMemo());
		po.setPackage_(info.getPackage_());
		po.setPayChannel(info.getPayChannel());
		po.setSeller(info.getSeller());
		po.setSellerAccountBank(info.getSellerAccountBank());
		po.setSellerAccountNo(info.getSellerAccountNo());
		po.setSellerAddress(info.getSellerAddress());
		po.setSellerFax(info.getSellerFax());
		po.setSellerMan(info.getSellerMan());
		po.setBuyerDate(info.getBuyerDate());
		po.setSellerDate(info.getSellerDate());
		contractDAO.update(po);
		
		contractDAO.deleteAllItems(info.getContractNo());
		
		
		if(items!=null){
			for(TContractItem item:items){
				item.setContractNo(info.getContractNo());
				contractDAO.saveDetail(item);
			}
		}
		
	}
	
	
	
	
}

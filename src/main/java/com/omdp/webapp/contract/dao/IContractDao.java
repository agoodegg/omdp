package com.omdp.webapp.contract.dao;

import java.util.List;

import com.omdp.webapp.base.common.dao.Dao;
import com.omdp.webapp.base.common.model.Page;
import com.omdp.webapp.model.TContractInfo;
import com.omdp.webapp.model.TContractItem;
import com.omdp.webapp.model.TUser;


public interface IContractDao<T> extends Dao<T> {

	public List<TContractInfo> queryContractInfo(TContractInfo info, TUser u, Page page);

	public void trashContract(String contractNo, TUser u);
	
	public TContractInfo queryContractByContractNo(String contractNo);

	public void saveDetail(TContractItem item);

	public void updateContractTotal(String contractNo);

	public TContractInfo queryContractById(Integer id);

	public List<TContractItem> queryContractItemByContractNo(String contractNo, TUser u);

	public void deleteAllItems(String contractNo);

	public TContractInfo findContractByOrdernum(String ordernum, TUser u);

	public void genContractFromOrder(TContractInfo contract, List<TContractItem> itemList);

}

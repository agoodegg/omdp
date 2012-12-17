package com.omdp.webapp.stat.dao;

import java.math.BigDecimal;
import java.util.List;

import com.omdp.webapp.base.common.dao.Dao;
import com.omdp.webapp.base.common.model.Page;
import com.omdp.webapp.model.TUser;


public interface CustomerBusiStatDAO<T> extends Dao<T> {

	public List<Object[]> querySql(String sql, TUser u);

	public List<Object[]> queryNativeSql(String sql, TUser u);
	
	public List<Object[]> queryNativeSql(String sql, TUser u, Page page);

	public List<Object> nativeQuery(String countSql);

}

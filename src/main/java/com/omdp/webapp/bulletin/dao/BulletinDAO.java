 package com.omdp.webapp.bulletin.dao;

import java.util.List;

import com.omdp.webapp.base.common.dao.Dao;
import com.omdp.webapp.base.common.model.Page;
import com.omdp.webapp.model.TBulletinInfo;
import com.omdp.webapp.model.TUser;


public interface BulletinDAO<T> extends Dao<T>{

	public List<TBulletinInfo> queryBulletInfoList(String sql, Page page);

	public TBulletinInfo load(Integer id, TUser u);

	public void executeUpdate(String updateSql);

	
}

package com.omdp.webapp.bulletin.service;

import java.util.List;

import com.omdp.webapp.base.common.model.Page;
import com.omdp.webapp.model.TBulletinInfo;
import com.omdp.webapp.model.TUser;


public interface BulletinService {

	public void saveNewOrder(TBulletinInfo info, TUser u);

	public List<TBulletinInfo> queryBulletInfoList(TBulletinInfo info, Page page, TUser u);

	public List<TBulletinInfo> queryCurrentBulletins();

	//根据id载入公告
	public TBulletinInfo loadBulletin(TBulletinInfo info, TUser u);

	public void removeBulletinByMark(TBulletinInfo info, TUser u);

}

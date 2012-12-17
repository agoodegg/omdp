package com.omdp.webapp.bulletin.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.transaction.annotation.Transactional;

import com.omdp.webapp.base.common.model.Page;
import com.omdp.webapp.bulletin.dao.BulletinDAO;
import com.omdp.webapp.bulletin.service.BulletinService;
import com.omdp.webapp.model.TBulletinInfo;
import com.omdp.webapp.model.TUser;

@Transactional
public class BulletinServiceImpl implements BulletinService {

	@Resource
	private BulletinDAO<TBulletinInfo> bulletinDAO;

	public void saveNewOrder(TBulletinInfo info, TUser u) {
		
		info.setBulletinStatus("0");
		info.setCreateTime(new Date());
		info.setCreator(String.valueOf(u.getUserName()));
		
		bulletinDAO.save(info);
	}

	public List<TBulletinInfo> queryBulletInfoList(TBulletinInfo info, Page page, TUser u) {
		String sql = " from TBulletinInfo t where t.bulletinStatus <> '2' order by t.createTime desc";
		String countSql = " select count(*) from TBulletinInfo t where t.bulletinStatus <> '2' ";
		int total = ((Long)(bulletinDAO.query(countSql).get(0))).intValue();
		page.setTotalCount(total);
		
		return bulletinDAO.queryBulletInfoList(sql,page);
	}

	
	public List<TBulletinInfo> queryCurrentBulletins() {
		String sql = " from TBulletinInfo t where t.bulletinStatus='0' and (t.endTime is null or t.endTime > now() ) order by t.createTime desc";
		Page p = new Page();
		p.setPageSize(3);
		p.setPageNo(1);
		return bulletinDAO.queryBulletInfoList(sql,p);
	}

	public TBulletinInfo loadBulletin(TBulletinInfo info, TUser u) {
		TBulletinInfo bulletin = bulletinDAO.load(info.getId(),u);
		return bulletin;
	}

	public void removeBulletinByMark(TBulletinInfo info, TUser u) {
		
		String updateSql = "update TBulletinInfo u set u.bulletinStatus='2' where u.id = " +info.getId();
		bulletinDAO.executeUpdate(updateSql);
	}
	
	
	
}

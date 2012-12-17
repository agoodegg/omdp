package com.omdp.webapp.sys.index.dao;

import com.omdp.webapp.base.common.dao.Dao;


public interface IndexDAO<T> extends Dao<T> {

	public Long getIntegerResultBySql(String sql);

}

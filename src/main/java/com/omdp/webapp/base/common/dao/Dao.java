package com.omdp.webapp.base.common.dao;

import java.io.Serializable;
import java.util.List;

public interface Dao<T> {

	public abstract void delete(Serializable serializable);

	public abstract void deleteAll(List list);

	public abstract Object get(Serializable serializable);

	public abstract Object load(Serializable serializable);

	public abstract List loadAll();

	public abstract T save(T obj);

	public abstract void saveOrUpdate(Object obj);

	public abstract void saveOrUpdateAll(List list);

	public abstract void update(T obj);

	public abstract void execute(String s, List list);

	public abstract List query(String s);
}

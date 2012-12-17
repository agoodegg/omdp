package com.omdp.webapp.base.common.dao.jpa;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.orm.jpa.JpaTemplate;
import org.springframework.orm.jpa.support.JpaDaoSupport;
import org.springframework.stereotype.Repository;

import com.omdp.webapp.base.common.dao.Dao;

@Repository
public class AbstractJpaDAO<T>  extends JpaDaoSupport implements Dao<T>{

	@PersistenceContext
	public void setPersistenceContext(EntityManager entityManager)
    {
        super.setEntityManager(entityManager);
        super.setJpaTemplate(new JpaTemplate(entityManager));
    }

	public void delete(Serializable serializable) {
		
	}

	public void deleteAll(List list) {
		
	}

	public void execute(String s, List list) {
		
	}

	public Object get(Serializable serializable) {
		return null;
	}

	public Object load(Serializable serializable) {
		return null;
	}

	public List loadAll() {
		return null;
	}

	public List query(String s) {
		Query q = this.getJpaTemplate().getEntityManager().createQuery(s);
		return q.getResultList();
	}

	public T save(T obj) {
		this.getJpaTemplate().getEntityManager().persist(obj);
		return obj;
	}

	public void saveOrUpdate(Object obj) {
		
	}

	public void saveOrUpdateAll(List list) {
		
	}

	public void update(T obj) {
		this.getJpaTemplate().getEntityManager().merge(obj);
	}
	
	
	
}

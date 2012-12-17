package com.omdp.webapp.sys.resource.dao.impl;

import org.springframework.stereotype.Repository;

import com.omdp.webapp.base.common.dao.jpa.AbstractJpaDAO;
import com.omdp.webapp.model.TSysParam;
import com.omdp.webapp.sys.resource.dao.ResourceDAO;


@Repository
public class ResourceDAOImpl extends AbstractJpaDAO<TSysParam> implements ResourceDAO<TSysParam> {

}

package org.tigersndragons.salonbooks.dao.impl;

import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.tigersndragons.salonbooks.dao.EntityRepository;

public class EntityRepositoryImpl implements EntityRepository {
	private static Logger logger = LoggerFactory.getLogger(EntityRepositoryImpl.class);
	@Autowired
	SessionFactory sessionFactory;
}

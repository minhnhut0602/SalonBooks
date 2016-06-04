package org.tigersndragons.salonbooks.dao.impl;
import org.hibernate.EmptyInterceptor;

public class AuditTrailInterceptor extends EmptyInterceptor{
	private static final long serialVersionUID = 1L;
	
	private static org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(AuditTrailInterceptor.class);
	

}

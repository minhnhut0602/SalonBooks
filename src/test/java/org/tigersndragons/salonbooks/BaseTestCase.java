package org.tigersndragons.salonbooks;

import org.hibernate.SessionFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.*;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.tigersndragons.salonbooks.core.TestContextConfiguration;
import org.tigersndragons.salonbooks.model.Person;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes={
		TestContextConfiguration.class
})
//locations={"testContext.xml"})
public abstract class BaseTestCase extends AbstractTransactionalJUnit4SpringContextTests{
	
	protected Logger logger = LoggerFactory.getLogger(getClass());
	private SessionFactory sessionFactory;
	
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	protected Person getDefaultPerson() {
		Person person = new Person();
		person.setId(0L);
		return  person;
	}
}

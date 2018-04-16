package cn.itcast.test;

import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.classic.Session;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cn.itcast.test.entity.Person;
import cn.itcast.test.service.TestService;

public class TestMerge {
	
	ClassPathXmlApplicationContext ctx;

	@Before
	public void loadCtx() {
		
		ctx = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
	}
	
	@Test
	public void testSpring() {
		TestService ts = (TestService)ctx.getBean("testService");
		ts.say();
	}
	@Test
	public void testHibernate() {
		SessionFactory sf = (SessionFactory)ctx.getBean("sessionFactory");
		Session session = sf.openSession();
		Transaction transaction = session.beginTransaction();
		session.save(new Person("用户1"));
		transaction.commit();
		session.close();
	}

	@Test
	public void testServiceAndDao() {
		TestService ts = (TestService)ctx.getBean("testService");
		ts.save(new Person("用户2"));
	}

	@Test
	public void testReadOnlyTransaction() {
		TestService ts = (TestService)ctx.getBean("testService");
		//在只读方法中进行新增数据，如果能够新增数据成功说明事务控制失败，否则事务控制成功
		Person p = ts.findPersonById("402881a94f0639df014f0639e0c50000");
		System.out.println("人员的名称为：" + p.getName());
	}
	
	@Test
	public void testRollbackTransaction() {
		TestService ts = (TestService)ctx.getBean("testService");
		//在保存数据之后，故意地抛出异常；查看新增数据是否插入成功，如果成功说明事务控制失败，否则说明控制没问题
		ts.save(new Person("用户3"));
	}
	
}

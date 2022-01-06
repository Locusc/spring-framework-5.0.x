package cn.locusc.chapter4.code.test;

import cn.locusc.chapter4.code.pojo.User;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author Jay
 * 7.测试
 * 2022/1/5
 */
public class ResolveXmlTest {

	@Test
	public void testResolveXml() {
		ApplicationContext bf =
				new ClassPathXmlApplicationContext("cn.locusc\\chapter4\\resolveTest.xml");

		User testBean = (User) bf.getBean("testBean");
		System.out.println(testBean.toString());
	}

}

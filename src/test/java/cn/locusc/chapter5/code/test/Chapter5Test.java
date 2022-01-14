package cn.locusc.chapter5.code.test;

import cn.locusc.chapter5.code.pojo.Car;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Chapter5Test {

	@Test
	public void fbTest() {
		ApplicationContext bf =
				new ClassPathXmlApplicationContext("cn.locusc\\chapter5\\fbTest.xml");

		Car testBean = (Car) bf.getBean("car");
		System.out.println(testBean.toString());
	}

}

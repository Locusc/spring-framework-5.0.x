package cn.locusc.chapter5.code.test;

import cn.locusc.chapter2.code.MyTestBean;
import cn.locusc.chapter5.code.fb.CarFactoryBean;
import cn.locusc.chapter5.code.pojo.Car;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Chapter5Test {

	@Test
	public void fbTest() {
		ApplicationContext bf =
				new ClassPathXmlApplicationContext("cn.locusc\\chapter5\\fbTest.xml");

		//Car carBean = (Car) bf.getBean("car");
		//System.out.println(carBean.toString());
		// cn.locusc.chapter5.code.pojo.Car@2e1c7a6e

		//CarFactoryBean carFactoryBean = (CarFactoryBean)bf.getBean("&car");
		//System.out.println(carFactoryBean.getCarInfo());
		// cn.locusc.chapter5.code.fb.CarFactoryBean@7af8a433

		MyTestBean test = (MyTestBean) bf.getBean("test");
		System.out.println(test.toString());
	}
}

package cn.locusc.chapter3.code;

import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {

	public static void main(String[] args) {
		ApplicationContext bf = new ClassPathXmlApplicationContext("cn.locusc\\lookupTest.xml");
		GetBeanTest getBeanTest = (GetBeanTest) bf.getBean("getBeanTest");
		getBeanTest.showMe();


		ApplicationContext bf1 = new ClassPathXmlApplicationContext("cn.locusc\\replaceMethodTest.xml");
		TestChangeMethod testChangeMethod = (TestChangeMethod) bf1.getBean("testChangeMethod");
		testChangeMethod.changeMe();
	}

}

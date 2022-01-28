package cn.locusc.chapter5.code.circle;

import org.junit.Test;
import org.springframework.beans.factory.BeanCurrentlyInCreationException;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 1.构造器循环依赖:
 * 表示通过构造器注入构成的循环依赖, 此依赖是无法解决的,
 * 只能抛出BeanCurrentlyInCreationException异常表示循环依赖
 *
 * 如在创建TestA类时, 构造器需要TestB类, 那将去创建TestB, 在创建TestB类时
 * 又发现需要TestC类, 则又去创建TestC, 最终在创建TestC时发现又需要TestA,
 * 从而形成一个环, 没办法创建
 *
 * Spring容器将每一个正在创建的Bean标识符放在一个"当前创建bean池中", bean标识
 * 符在创建过程中将一直保持在这个池中， 因此如果在创建bean过程中发现自己已经在
 * "当前创建bean池"里时, 将抛出BeanCurrentlyInCreationException异常表示
 * 循环依赖, 而对于创建完毕的bean将从"当前创建bean池"中清除掉
 */
public class CirCleTest {

	@Test(expected = BeanCurrentlyInCreationException.class)
	public void test() throws Throwable {
		try {
			// org.springframework.beans.factory.BeanCreationException:
			// Error creating bean with name 'testA' defined in class path resource [cn.locusc/chapter5/circleTest.xml]:
			// Cannot resolve reference to bean 'testB' while setting constructor argument;
			// nested exception is org.springframework.beans.factory.BeanCreationException:
			// Error creating bean with name 'testB' defined in class path resource [cn.locusc/chapter5/circleTest.xml]:
			// Cannot resolve reference to bean 'testC' while setting constructor argument;
			// nested exception is org.springframework.beans.factory.BeanCreationException:
			// Error creating bean with name 'testC' defined in class path resource [cn.locusc/chapter5/circleTest.xml]:
			// Cannot resolve reference to bean 'testA' while setting constructor argument;
			// nested exception is org.springframework.beans.factory.BeanCurrentlyInCreationException:
			// Error creating bean with name 'testA':
			// Requested bean is currently in creation: Is there an unresolvable circular reference?
			ClassPathXmlApplicationContext classPathXmlApplicationContext =
					new ClassPathXmlApplicationContext("cn.locusc\\chapter5\\circleTest.xml");
			Object testA = classPathXmlApplicationContext.getBean("testA");
			System.out.println("testA:" + testA);
		} catch (Exception e) {
			throw e.getCause().getCause().getCause();
			// e.printStackTrace();
		}
	}

}

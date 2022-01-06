package cn.locusc.chapter4.code.parser;

import cn.locusc.chapter4.code.pojo.User;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser;
import org.springframework.util.StringUtils;
import org.w3c.dom.Element;

/**
 * @author Jay
 * 3.创建一个文件, 实现BeanDefinitionParser
 * 用来解析XSD文件中的定义和组件定义
 * 2022/1/5
 */
public class UserDefinitionParser extends AbstractSingleBeanDefinitionParser {

	// Element对应的类
	protected Class<?> getBeanClass(Element element) {
		return User.class;
	}

	// 从element中解析并提取对应的元素
	protected void doParse(Element element, BeanDefinitionBuilder beanDefinitionBuilder) {
		String userName = element.getAttribute("userName");
		String email = element.getAttribute("email");
		// 将提取的数据放入到BeanDefinitionBuilder中
		// 待到完成所有bean的解析后统一注册到beanFactory中
		if(StringUtils.hasText(userName)) {
			beanDefinitionBuilder.addPropertyValue("userName", userName);
		}
		if(StringUtils.hasText(email)) {
			beanDefinitionBuilder.addPropertyValue("email", email);
		}
	}

}

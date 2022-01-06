package cn.locusc.chapter4.code.handler;

import cn.locusc.chapter4.code.parser.UserDefinitionParser;
import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

/**
 * @author Jay
 * 4.创建一个handler文件, 扩展自NamespaceHandlerSupport
 * 目的是将组件注册到Spring容器
 * 2022/1/5
 */
public class MyNamespaceHandler extends NamespaceHandlerSupport {

	/**
	 * 当遇到自定义标签<user:aaa
	 * 这样类似于以user开头的元素, 就会把这个元素扔给对应的UserDefinitionParser去解析
	 */
	@Override
	public void init() {
		registerBeanDefinitionParser("user", new UserDefinitionParser());
	}

}

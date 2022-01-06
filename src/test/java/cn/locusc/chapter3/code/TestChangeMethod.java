package cn.locusc.chapter3.code;

import org.springframework.beans.factory.support.MethodReplacer;

import java.lang.reflect.Method;

public class TestChangeMethod {

	public void changeMe() {
		System.out.println("changeMe");
	}

	public static class TestMethodReplacer implements MethodReplacer {

		@Override
		public Object reimplement(Object obj, Method method, Object[] args) throws Throwable {
			System.out.println("i replace method");
			return null;
		}

	}
}

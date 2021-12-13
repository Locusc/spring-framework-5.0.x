package org.springframework;

public class HelloWorld {

	public void sayHello () {
		System.out.println("Hello AspectJ !");
	}

	public static void main(String[] args) {
		HelloWorld h = new HelloWorld();
		h.sayHello();
	}

}

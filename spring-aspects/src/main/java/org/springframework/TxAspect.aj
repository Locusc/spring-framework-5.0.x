package org.springframework;

public aspect TxAspect {

	void around():call(void sayHello()) {
		System.out.println("Transaction Begin");
		proceed();
		System.out.println("Transaction End");
	}

}

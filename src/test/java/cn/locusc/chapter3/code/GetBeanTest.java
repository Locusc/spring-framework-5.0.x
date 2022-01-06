package cn.locusc.chapter3.code;

public abstract class GetBeanTest {

	public void showMe() {
		this.getBean().showMe();
	}


	// @Lookup
	public abstract User getBean();

}

package cn.locusc.chapter4.code.pojo;

/**
 * @author Jay
 * 首先创建一个POJO, 用来接收配置文件
 * 2022/1/5
 */
public class User {

	private String userName;

	private String email;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "User{" +
				"userName='" + userName + '\'' +
				", email='" + email + '\'' +
				'}';
	}
}

package com.ygm.hotel;




// V2 使用 HttpServlet
public class WebPostData {
	private String username;
	private String pwd;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	@Override
	public String toString() {
		return "WebPostDataServlet{" +
				"username='" + username + '\'' +
				", pwd='" + pwd + '\'' +
				'}';
	}
}
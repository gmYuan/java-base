package com.ygm;

public class MyLogService {
	@Log
	public void queryDataBase(int param) {
		System.out.println("query db：" + param);
	}

	@Log(value = "myError")
	public void provideHttpRes(String param) {
		System.out.println("provide Http data：" + param);
	}

	public void noLog() {
		System.out.println("no Log Called");
	}

}

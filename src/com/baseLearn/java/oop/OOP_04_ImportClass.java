package com.baseLearn.java.oop;

// 导入不同文件下的 同名类
import com.baseLearn.java.base.Test;
import static com.baseLearn.java.oop.OOP_03_overLoad.MAX_SPEED;

// 相同目录下的类，可以不用显式声明导入
// import com.baseLearn.java.oop.Test;


public class OOP_04_ImportClass {
	public static void main(String[] args) {
//		Test test1 = new Test();
		Test.main();
    com.baseLearn.java.oop.Test.main(new String[]{"1"});
		System.out.printf("OOP_03_overLoad.MAX_SPEED是：%d", MAX_SPEED);
	}
}

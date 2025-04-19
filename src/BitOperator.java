public class BitOperator {
	public static void main(String[] args) {
		// 位运算：按位与；按位或；按位异或；按位取反
		int a = 10;
		int b = 99;
		
		int num1 = a & b;
		int num2 = a | b;
		int num3 = a ^ b;
		int num4 = ~a;
		int num5 = ~b;
		
		String binA = Integer.toBinaryString(a);
		String binB = Integer.toBinaryString(b);
		System.out.println("a的二进制是：" + binA); // 25个0 + 0001010
		System.out.println("b的二进制是：" + binB); // 25个0 + 1100011
		
		String binNum1 = Integer.toBinaryString(num1);
		System.out.println("按位与 二进制结果是：" + binNum1);  // 000..10
		System.out.println("按位与 结果是：" + num1);  // 2
		
		
		System.out.println("--------------------");
		// 25个0 + 0001010
		// 25个0 + 1100011
		// 25个0 + 1101011==> 对应10进制就是107
		String binNum2 = Integer.toBinaryString(num2);
		System.out.println("按位或 二进制结果是：" + binNum2);
		System.out.println("按位或 结果是：" + num2);
		
		System.out.println("按位异或 结果是：" + num3);
		System.out.println("按位取反a 结果是：" + num4);
		System.out.println("按位取反b 结果是" + num5);
		

	}
}

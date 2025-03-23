public class Main {
	public static void main(String[] args) {
		System.out.printf("我是变量\n");
		// byte类型占1个字节(8位)，表示范围是 -128 ~ 12
		byte num1 = 127;
		System.out.println("---byte类型---");
		System.out.println(num1);

		// short类型占2个字节，表示范围是 -32768 ~ 32767
		short num2 = 200;
		System.out.println("---short类型---");
		System.out.println(num2);

		// int类型占4个字节，表示范围大概是 21亿
		int num3 = 1234567890;

		// long类型占8个字节，表示范围大概是 [-2^63, 2^63-1]
		long num4 = 1234567891234567891L;
		System.out.println("---long类型---");
		System.out.println(num4);

	}
}
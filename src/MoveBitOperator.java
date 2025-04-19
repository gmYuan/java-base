public class MoveBitOperator {
	public static void main(String[] args) {
		// 移位位运算：左移<<;  右移>>;  无符号右移>>>
		
		int a = 10;
		int a1 = a << 1;
		int a2 = a << 2;
		System.out.println("a的2进制表示为:" + Integer.toBinaryString(a));
		System.out.println("a的10进制结果是" + a);
		
		// 左移效果理解：在2进制末尾补充 n个0，从而扩大2^n 倍
		System.out.println("a1的2进制表示为:" + Integer.toBinaryString(a1));
		System.out.println("a左移1位的 10进制结果是" + a1);
		
		System.out.println("a2的2进制表示为:" + Integer.toBinaryString(a2));
		System.out.println("a左移2位的结果是" + a2);
		
		int b = -100;
		int a3 = b >> 1;
		int a4 = b >> 2;
		
		System.out.println("----------------------------------------------------");
		System.out.println("b的2进制表示为:" + Integer.toBinaryString(b));
		System.out.println("b的10进制结果是" + b);
		
		// 保留符号 + 右移效果理解：在2进制末尾去除 n个0，从而缩小2^n 倍，前面根据符号位补充0/1
		System.out.println("a3的2进制表示为:" + Integer.toBinaryString(a3));
		System.out.println("a右移1位的 10进制结果是" + a3);
		
		System.out.println("a4的2进制表示为:" + Integer.toBinaryString(a4));
		System.out.println("a右移2位的 10进制结果是" + a4);
		
		// 不保留符号位 + 右移动效果理解：在2进制末尾去除 n个0 +前面固定补充0/1
		int a5  = b >>> 1;
		System.out.println("----------------------------------------------------");
		System.out.println("b的2进制表示为:" + Integer.toBinaryString(b));
		System.out.println("b的10进制结果是" + b);
		
		// 这里输出的是 111 1111 1111 1111 1111 1111 1100 1110，但其实只有31位置
		// 完整32位应该是 0111 1111 1111 1111 1111 1111 1100 1110
		System.out.println("a5的2进制表示为:" + Integer.toBinaryString(a5));
		System.out.println("a无符号右移1位的 10进制结果是" + a5);
		
	}
}

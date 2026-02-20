package com.ygm.cmd;


import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

public class PbFork {
	public static void main(String[] args) throws IOException, InterruptedException {
		// 请在这里使用Java代码fork一个子进程
		// 将fork的子进程的标准输出重定向到指定文件：工作目录下 名为output.txt的文件
		// 工作目录是 项目目录下的working-directory目录（可以用getWorkingDir() 方法得到这个目录对应的File对象）
		// 传递的命令是sh run.sh
		// 环境变量是AAA=123


    // 1. 可执行程序;  2. 参数
    // ProcessBuilder 是 Java 中用来创建和管理操作系统进程的类
		// 创建一个 ProcessBuilder 对象，准备执行命令 sh run.sh
		// 相当于在终端里运行 sh run.sh
		ProcessBuilder pb = new ProcessBuilder("sh", "run.sh");
		
		// 3. 工作目录
		// 设置子进程的工作目录为  getWorkingDir() 返回的目录
		// 也就是说 sh run.sh 会在 项目根目录/working-directory/ 这个目录下 执行
		pb.directory(getWorkingDir());
		
		// 4. 环境变量
		// pb.environment() 返回的是一个可修改的 Map，初始值继承自当前 Java 进程的环境变量
		// 这里 向子进程的环境变量中添加一个 AAA=123
		Map<String, String> envs = pb.environment();
		envs.put("AAA", "123");

		// 5.1 表示子进程继承父进程的 IO 流:
		// - 子进程的标准输入来自父进程（通常是控制台）
		// - 子进程的标准输出和标准错误会传到父进程（直接在控制台显示）
		// 也就是说，子进程的 stdout / stderr 不会写入文件，而是直接显示在运行 Java 程序的控制台
		// pb.inheritIO();

		// 5.2 把子进程的标准输出（stdout）重定向到指定文件
		pb.redirectOutput(getOutputFile());


		// 6. 启动子进程 并 等待子进程结束
		// pb.start() 返回一个 Process 对象
		// 这个 Process 对象 代表 刚刚创建的 子进程
		// process.waitFor() 会阻塞当前线程，直到子进程结束
		pb.start().waitFor();

	}

	private static File getWorkingDir() {
		//  获取 JVM 启动时的 当前工作目录（通常就是项目根目录）
		Path projectDir = Paths.get(System.getProperty("user.dir"));
		// 在路径后面拼接 working-directory 子目录
		// .toFile() 把 Path 转成 File 对象
		return projectDir.resolve("working-directory").toFile();
	}

	private static File getOutputFile() {
		// 返回 "项目根目录/working-directory/output.txt" 的 File 对象
		return new File(getWorkingDir(), "output.txt");
	}
}
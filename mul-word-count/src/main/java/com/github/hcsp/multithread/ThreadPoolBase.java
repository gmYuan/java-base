package com.github.hcsp.multithread;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

public class ThreadPoolBase {

    // 线程池的 基础使用方法
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService threadPool = Executors.newFixedThreadPool(10);

        Future<Integer> future1 = threadPool.submit(new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                Thread.sleep(1000);
                return 111;
            }
        });

        Future<String> future2 = threadPool.submit(new Callable<String>() {
            @Override
            public String call() throws Exception {
                Thread.sleep(1000);
                return "abc";
            }
        });

        Future<Object> future3 = threadPool.submit(new Callable<Object>() {
            @Override
            public String call() throws Exception {
                throw new RuntimeException();
            }
        });

        System.out.println("future1的值是：" + future1.get());
        System.out.println("future2的值是：" + future2.get());
        System.out.println("future3的值是：" + future3.get());


    }
}

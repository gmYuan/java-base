package com.github.hcsp.multithread;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;

public class WordCount1 {
	private final int threadNum;
	private ExecutorService threadPool;

	public WordCount1(int threadNum) {
		this.threadNum = threadNum;
		this.threadPool = Executors.newFixedThreadPool(threadNum);
	}

	// 统计文件中各单词的数量
	public Map<String, Integer> count(File file) throws FileNotFoundException, ExecutionException, InterruptedException {
		// 开若干个线程, 每个线程去读取文件的一行内容，并将其中的单词统计结果返回
		// 最后，主线程将工作线程返回的结果汇总在一起
		BufferedReader reader = new BufferedReader(new FileReader(file));
		ArrayList<Future<Map<String, Integer>>> futures = new ArrayList<>();
		Map<String, Integer> finalResult = new HashMap<>();

		// 创建多个线程，同时读取 N 行单词统计
		for (int i = 0; i < threadNum; i++) {
			futures.add(threadPool.submit(new WorkerJob(reader)));
		}
		// 处理汇总结果
		 for (Future<Map<String, Integer>> future : futures) {
			 Map<String, Integer> lineMap = future.get();
			 mergeLineMap(lineMap, finalResult);
		 }
		 return finalResult;
	}

	private static class WorkerJob implements Callable {
		private BufferedReader reader;
		public WorkerJob(BufferedReader reader) {
			this.reader = reader;
		}

		@Override
		public Map<String,  Integer> call() throws Exception {
			String line;
			HashMap<String, Integer> result = new HashMap<>();
			while ((line = reader.readLine()) != null) {
				String[] words = line.split(" ");
				for (String word : words) {
					result.put(word, result.getOrDefault(word, 0) + 1);
				}
			}
			return result;
		}
	}

	private static void mergeLineMap(Map<String, Integer> lineMap, Map<String, Integer> finalResult) {
		for (Map.Entry<String, Integer> entry: lineMap.entrySet()) {
			String word = entry.getKey();
			int count = finalResult.getOrDefault(word, 0) + entry.getValue();
			finalResult.put(word, count);
		}
	}

}

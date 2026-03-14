package com.github.hcsp.multithread;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

public class MultiThreadWordCount1 {
	// 统计文件中各单词的数量
	public static Map<String, Integer> count(int threadNum, List<File> files) throws ExecutionException, InterruptedException {
		// 开若干个线程, 每个线程去读取文件的一行内容，并将其中的单词统计结果返回
		// 最后，主线程将工作线程返回的结果汇总在一起
		ExecutorService threadPool = Executors.newFixedThreadPool(threadNum);
		ArrayList<Future<Map<String, Integer>>> futures = new ArrayList<>();
		Map<String, Integer> finalResult = new HashMap<>();

		// 为每个文件提交一个任务
		for (File file : files) {
			futures.add(threadPool.submit(new FileWorkerJob(file)));
		}

		// 处理汇总结果
		for (Future<Map<String, Integer>> future : futures) {
			Map<String, Integer> fileResult = future.get();
			mergeLineMap(fileResult, finalResult);
		}

		threadPool.shutdown();
		return finalResult;
	}

	private static class FileWorkerJob implements Callable<Map<String, Integer>> {
		private File file;

		public FileWorkerJob(File file) {
			this.file = file;
		}

		@Override
		public Map<String, Integer> call() throws Exception {
			HashMap<String, Integer> result = new HashMap<>();
			try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
				String line;
				while ((line = reader.readLine()) != null) {
					String[] words = line.split("\\s+");
					for (String word : words) {
						if (!word.isEmpty()) {
							result.put(word, result.getOrDefault(word, 0) + 1);
						}
					}
				}
			}
			return result;
		}
	}

	private static void mergeLineMap(Map<String, Integer> lineMap, Map<String, Integer> finalResult) {
		for (Map.Entry<String, Integer> entry : lineMap.entrySet()) {
			String word = entry.getKey();
			int count = finalResult.getOrDefault(word, 0) + entry.getValue();
			finalResult.put(word, count);
		}
	}

}

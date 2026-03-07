package com.crawler;


import org.kohsuke.github.GHIssueState;
import org.kohsuke.github.GHPullRequest;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GitHub;

import java.io.File;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class CrawlerV2 {
	public static void main(String[] args) {
		// 强制使用 TLS 1.2+，避免与 GitHub API 握手时被远端关闭连接
		System.setProperty("https.protocols", "TLSv1.2,TLSv1.3");
		// 拉长连接/读取超时（毫秒），避免访问 api.github.com 时 Read timed out（如网络较慢或需代理）
		System.setProperty("sun.net.client.defaultConnectTimeout", "60000");  // 60 秒
		System.setProperty("sun.net.client.defaultReadTimeout", "60000");    // 60 秒
		try {
			savePullRequestsToCSV("golang/go", 10, new File("pulls2.csv"));
		} catch (Exception e) {
			System.err.println("出错: " + e.getMessage());
			e.printStackTrace();
		}
	}

	// 给定一个仓库名，例如"golanglgo"，或者"gradle/gradle"，
	// 读取前n个Pullrequest并保存至 csvFile指定的文件中，其格式为：
	//  number,author,title
	//  12345, blindpirate, 这是一个标题
	//  12345, FrankFang,   这是第二个标题
	public static void savePullRequestsToCSV(String repo, int count, File csvFile) throws IOException {
		System.out.println("正在处理仓库：" + repo);
		// 创建 github 的对象连接
		GitHub github = GitHub.connectAnonymously();
		// 连接到 repo仓库
		GHRepository repository = github.getRepository(repo);
		System.out.println("正在处理repository：" + repository);

		List<GHPullRequest> pullRequests = repository.getPullRequests(GHIssueState.OPEN);
		List<String> lines = pullRequests
				.stream()
				.limit(count)  // 只取前 10 个
				.map(CrawlerV2::getLine)
				.collect(Collectors.toList());

		lines.add(0, "number,author,title");  // 在开头插入表头
		Files.write(csvFile.toPath(), lines);
	}

	public static String getLine(GHPullRequest pullRequest) {
		try {
			return pullRequest.getNumber() + "," + pullRequest.getUser().getLogin() + "," + pullRequest.getTitle();
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}
}


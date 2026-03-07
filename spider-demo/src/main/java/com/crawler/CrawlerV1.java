package com.crawler;


import org.kohsuke.github.GHIssueState;
import org.kohsuke.github.GHPullRequest;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GitHub;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Iterator;

public class CrawlerV1 {
	public static void main(String[] args) {
		// 强制使用 TLS 1.2+，避免与 GitHub API 握手时被远端关闭连接
		System.setProperty("https.protocols", "TLSv1.2,TLSv1.3");
		// 拉长连接/读取超时（毫秒），避免访问 api.github.com 时 Read timed out（如网络较慢或需代理）
		System.setProperty("sun.net.client.defaultConnectTimeout", "60000");  // 60 秒
		System.setProperty("sun.net.client.defaultReadTimeout", "60000");    // 60 秒
		try {
			savePullRequestsToCSV("golang/go", 10, new File("pulls.csv"));
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
		// 只迭代前 count 条 PR，不要 toList()，否则会拉取全部分页导致超时
		Iterator<GHPullRequest> it = repository.getPullRequests(GHIssueState.OPEN).iterator();
		StringBuilder CsvContent = new StringBuilder("number,author,title\n");
		int actualCount = 0;
		while (it.hasNext() && actualCount < count) {
			GHPullRequest pr = it.next();
			int number = pr.getNumber();
			String author = pr.getUser().getLogin();
			String title = pr.getTitle();
			CsvContent.append(number).append(",").append(author).append(",").append(title).append("\n");
			actualCount++;
		}
		if (actualCount == 0) {
			System.out.println("该仓库暂无打开的 Pull Request");
			Files.write(csvFile.toPath(), "number,author,title\n".getBytes());
			return;
		}
		System.out.println("已获取 pullRequests：" + actualCount + " 条");
		System.out.println("正在写入 CSV：" + csvFile.getAbsolutePath());
		Files.write(csvFile.toPath(), CsvContent.toString().getBytes());
	}
}


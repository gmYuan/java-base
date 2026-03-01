package com.ygm;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

public class Main {
	public static void main(String[] args) throws IOException {
		CloseableHttpClient httpclient = HttpClients.createDefault();
    // 使用标准规范，更宽松
		RequestConfig requestConfig = RequestConfig.custom()
				.setCookieSpec(CookieSpecs.STANDARD)
				.build();
		HttpGet httpGet = new HttpGet("https://github.com/gradle/gradle/issues");
		httpGet.setConfig(requestConfig);

		CloseableHttpResponse response = httpclient.execute(httpGet);
		try {
			// S1 获取到 响应体内容（字节流格式）
			// System.out.println(response.getStatusLine());
			HttpEntity entity1 = response.getEntity();
			InputStream is = entity1.getContent();

			// S2 commons-io 来解析 inputStream 为 String
			String html = IOUtils.toString(is, "utf-8");
			// System.out.println(html);

			// S3 jsoup 来解析 html
			Document document = Jsoup.parse(html);
			ArrayList<Element> elements = document.select("a[data-testid*=issue-pr-title]");
			for (Element element : elements) {
				System.out.println(element.text());
				System.out.println(element.attr("href"));
			}
		} finally {
			response.close();
		}

	}
}
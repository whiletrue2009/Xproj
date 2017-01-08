package com.whiletrue.xproj.xstock.xdata;

import java.io.IOException;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class HttpUtil {

	private static final Logger logger = LoggerFactory
			.getLogger(HttpUtil.class);

	private HttpUtil() {

	}

	public static String get(String url) {
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpGet httpget = new HttpGet(url);
		logger.info("executing request " + httpget.getURI());
		// 执行get请求.
		CloseableHttpResponse response;
		try {
			response = httpclient.execute(httpget);

			try {
				// 获取响应实体
				HttpEntity entity = response.getEntity();
				// 打印响应状态
				logger.info(response.getStatusLine().toString());
				if (entity != null) {
					logger.info("getContentEncoding : {}",
							entity.getContentEncoding());
					logger.info("getContentType : {}", entity.getContentType());

					Header header = entity.getContentType();
					String contentTypeValue = header.getValue();
					String encoding = contentTypeValue
							.substring(contentTypeValue.indexOf("charset") + 8);
					logger.info(encoding);
					return EntityUtils.toString(entity, encoding);
				}
			} finally {
				response.close();
			}
		}   catch (IOException e) {
			logger.error(e.toString(),e);
		}
		return "";
	}

}

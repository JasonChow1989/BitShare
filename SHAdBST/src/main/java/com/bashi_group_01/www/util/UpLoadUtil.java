package com.bashi_group_01.www.util;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.HttpVersion;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.CoreProtocolPNames;

public class UpLoadUtil {
	
	public static String uploadFile(String url, HttpEntity entity) {
		
		@SuppressWarnings("resource")
		HttpClient httpClient = new DefaultHttpClient();// 开启一个客户端 HTTP 请求

		httpClient.getParams().setParameter(
				CoreProtocolPNames.PROTOCOL_VERSION, HttpVersion.HTTP_1_1);
		httpClient.getParams().setParameter(
				CoreConnectionPNames.CONNECTION_TIMEOUT, 50000);// 设置连接超时时间
		HttpPost httpPost = new HttpPost(url);// 创建 HTTP POST 请求
		// httpPost.addHeader("Accept","text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
		httpPost.setEntity(entity);
		try {
			HttpResponse httpResponse = httpClient.execute(httpPost);

			if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
				System.out.println("ssssssssss");
				InputStream inStream = httpResponse.getEntity().getContent();
				// 分段读取输入流数据
				ByteArrayOutputStream baos = new ByteArrayOutputStream();
				byte[] buf = new byte[1024];
				int len = -1;
				while ((len = inStream.read(buf)) != -1) {
					baos.write(buf, 0, len);
				}
				String inputContent = new String(baos.toByteArray());
				System.out.println(inputContent);
				return inputContent;
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (ConnectTimeoutException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (httpClient != null && httpClient.getConnectionManager() != null) {
				httpClient.getConnectionManager().shutdown();
			}
		}
		return "文件上传失败";
	}
}

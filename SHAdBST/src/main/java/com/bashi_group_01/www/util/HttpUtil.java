package com.bashi_group_01.www.util;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.bashi_group_01.www.domain.ResultRetrun;

public class HttpUtil {

	public static String doPost(String path, List<NameValuePair> list) {
		String result = "";
		try {
			HttpPost httpRequst = new HttpPost(path);// 创建HttpPost对象

			if (list != null) {
				httpRequst
						.setEntity(new UrlEncodedFormEntity(list, HTTP.UTF_8));
			}
			HttpResponse httpResponse = new DefaultHttpClient()
					.execute(httpRequst);
			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				System.out.println("===================");
				HttpEntity httpEntity = httpResponse.getEntity();
				result = EntityUtils.toString(httpEntity);// 取出应答字符串
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return result;
	}

	public static String doGet(String path, List<NameValuePair> list) {

		InputStream inputStream = null;
		ByteArrayOutputStream bos = null;
		String s ="";

		try {

			StringBuffer sb = new StringBuffer(path);
			if (list != null && list.size() > 0) {

				if (sb.indexOf("?") < 0) {
					sb.append("?");
				}
				for (NameValuePair entry : list) {
					sb.append(entry.getName().toString());
					sb.append("=");
					sb.append(entry.getValue().toString());
					sb.append("&");
				}

				sb.deleteCharAt(sb.length() - 1);
			}
			path = sb.toString();
			URL url = new URL(path);
			System.out.println(url);
			
			HttpURLConnection httpURLConnection = (HttpURLConnection) url
					.openConnection();

			httpURLConnection.setConnectTimeout(5000);
			httpURLConnection.setRequestMethod("GET");

			if (httpURLConnection.getResponseCode() == 200) {
				System.out.println("<<<<<<<<<");
				
				inputStream = httpURLConnection.getInputStream();
				System.out.println(inputStream);
				
				bos = new ByteArrayOutputStream();

				byte[] buffer = new byte[1024];

				int len = -1;
				while ((len = inputStream.read(buffer)) != -1) {
					bos.write(buffer);
				}
				
				 s = new String(bos.toByteArray(),"Utf-8");

			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (bos != null) {
					bos.close();
				}
				if (inputStream != null) {
					inputStream.close();
				}
			} catch (Exception e2) {
				// TODO: handle exception
				e2.printStackTrace();
			}
		}

		return s;
	}

	public static ResultRetrun ResultReturn(String result){
		
		ResultRetrun rr = new ResultRetrun();
		try {
			JSONObject jsonObject = new JSONObject(result);
			int resultCode = jsonObject.getInt("resultCode");
			if(resultCode==1){
				String resultDesc = jsonObject.getString("resultDesc");
				
				JSONArray jsonArray = jsonObject.getJSONArray("result");
				
				rr.setResult(jsonArray);
				rr.setResultCode(resultCode);
				rr.setResultDesc(resultDesc);
			}else if(resultCode==0){
				rr.setResultCode(resultCode);
			}
			
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return rr;
	}
	
	
}

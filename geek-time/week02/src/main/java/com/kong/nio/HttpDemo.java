package com.kong.nio;

import com.alibaba.fastjson.JSON;
import com.sun.org.slf4j.internal.Logger;
import com.sun.org.slf4j.internal.LoggerFactory;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

import java.io.IOException;

/**
 * @author: xiangbinkong
 * @Date: 2021/7/4 16:08
 * @Description:
 */
public class HttpDemo {

	private static final Logger log = LoggerFactory.getLogger(HttpDemo.class);

	public static void main(String[] args) {
		String questUrl = "http://localhost:8801";
		OkHttpClient okHttpClient = new OkHttpClient();
		Request request = new Request.Builder().url(questUrl).build();
		try {
			final Response response = okHttpClient.newCall(request).execute();
			if (response.isSuccessful()){
				final ResponseBody body = response.body();
				System.out.println(JSON.toJSONString(body));
			}
		} catch (IOException e) {
			log.error("okhttp request is exception", e);
		}
	}
}

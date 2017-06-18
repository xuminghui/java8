package com.dingdang.okhttp;

import java.util.concurrent.TimeUnit;

import org.junit.Test;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class OkHttpTest {
	@Test
	public void test() throws Exception {
		OkHttpClient okHttpClient = new OkHttpClient.Builder()
				.connectTimeout(5, TimeUnit.SECONDS)
				.readTimeout(5, TimeUnit.MINUTES)
				.build();
		Request request = new Request.Builder().url("http://localhost:8080/user/1")
				.get()
				.build();
		Response response = okHttpClient.newCall(request)
				.execute();
		String content = response.body()
				.string();
		System.out.println(content);
	}
}

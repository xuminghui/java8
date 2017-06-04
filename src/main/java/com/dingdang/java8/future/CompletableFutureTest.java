package com.dingdang.java8.future;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

import org.springframework.util.concurrent.ListenableFutureCallback;

import ch.qos.logback.core.net.SyslogOutputStream;

public class CompletableFutureTest {
	public static void main(String[] args) throws Exception {
		final CompletableFuture<String> future = new CompletableFuture<>();
		// 应用在测试领域，直接返回一个值.
		Future<String> result = CompletableFuture.completedFuture("test");

		// 创建一个CompletableFuture
		final CompletableFuture<String> future1 = CompletableFuture.supplyAsync(() -> "42");
		final CompletableFuture<Void> future2 = CompletableFuture.runAsync(() -> {
		});

		// thenApply (相当于Stream中的map)
		final CompletableFuture<Void> future3 = CompletableFuture.supplyAsync(() -> calculate())
				.thenApply(t -> {
					return Integer.parseInt(t);
				})
				.thenAccept(t -> System.out.println(t));

		// thenCompose(相当于Stream中的flatMap)
		CompletableFuture<CompletableFuture<String>> f1 = CompletableFuture.supplyAsync(() -> calculate())
				.thenApply(t -> CompletableFuture.supplyAsync(() -> t));
		CompletableFuture<String> f2 = CompletableFuture.supplyAsync(() -> calculate())
				.thenCompose(t -> CompletableFuture.supplyAsync(() -> t));
		Thread.sleep(100000000);
	}

	public static String calculate() {
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("running");
		return "12";
	}
}

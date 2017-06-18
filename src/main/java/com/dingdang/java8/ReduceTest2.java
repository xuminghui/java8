package com.dingdang.java8;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ReduceTest2 {
	private Map<Integer, Long> cache;

	public ReduceTest2() {
		cache = new HashMap<>();
		cache.put(0, 0l);
		cache.put(1, 1l);
	}

	long fibonacci(int x) {
		return cache.computeIfAbsent(x, n -> fibonacci(n - 1) + fibonacci(n - 2));
	}

	public static void main(String[] args) {
		ReduceTest2 rt = new ReduceTest2();
		System.out.println(rt.fibonacci(100));

		String result = Stream.of("ab", "bbbbbb", "c")
				.collect(Collectors.maxBy(Comparator.comparing(String::length)))
				.orElseThrow(RuntimeException::new);
		System.out.println(result);
		Integer i=10_000_000;
		System.out.println(i);
	}

}

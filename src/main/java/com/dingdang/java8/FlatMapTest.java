package com.dingdang.java8;

import java.util.Arrays;
import java.util.stream.Stream;

public class FlatMapTest {
	public static void main(String[] args) {
		Stream<String> stream=Stream.of("2 2","3 3");
		Stream<String> stream1=stream.flatMap(t->Arrays.stream(t.split(" ")));
		stream1.forEach(t->{
			System.out.println(t);
		});
	}
}

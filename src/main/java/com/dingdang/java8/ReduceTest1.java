package com.dingdang.java8;

import java.util.StringJoiner;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ReduceTest1 {

	public static void main(String[] args) {
		testCollector();
	}

	// collect的入参是一个Collector(收集器)
	public static String testCollector() {
		Stream<String> stream = Stream.of("abc", "def", "ghi");
		String result = stream.map(t -> t.substring(1))
				.collect(Collectors.joining(",", "[", "]"));
		return result;
	}

	// 通过reduce实现一个collector
	public static String testReduceCollector() {
		Stream<String> stream = Stream.of("abc", "def", "ghi");
		StringBuilder reduced = stream.reduce(new StringBuilder(), (builder, name) -> {
			if (builder.length() > 0)
				builder.append(", ");
			builder.append(name);
			return builder;
		}, (left, right) -> left.append(right));
		reduced.insert(0, "[");
		reduced.append("]");
		String result=reduced.toString();
		return result;
	}
	// 通过StringJoiner实现
	public static String testStringJoinerCollector() {
		Stream<String> stream = Stream.of("abc", "def", "ghi");
		StringJoiner reduced = stream.reduce(new StringJoiner(", ","[","]"), StringJoiner::add,StringJoiner::merge);
		String result=reduced.toString();
		return result;
	}
	
	

}

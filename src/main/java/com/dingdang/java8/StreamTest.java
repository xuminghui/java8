package com.dingdang.java8;

import java.util.function.UnaryOperator;
import java.util.stream.LongStream;
import java.util.stream.Stream;

public class StreamTest {
	public static void main(String[] args) {
		//定义一个函数接口
		UnaryOperator<String> a=t->t+1;
		System.out.println(a.apply("3"));
		//stream计算
		long total=Stream.iterate(1l, t->t+1).limit(90000000).reduce(0l, Long::sum);
		System.out.println(total);
		//并行流处理很慢，基本计算不出来结果
		//long total2=Stream.iterate(1l, t->t+1).limit(90000000).parallel().reduce(0l, Long::sum);
		//System.out.println(total);
		//使用LongStream
		LongStream longStream=LongStream.rangeClosed(1, 90000000);
		long total3=longStream.parallel().reduce(0,Long::sum);
		System.out.println(total3);
	}
}

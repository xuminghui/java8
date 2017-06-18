package com.dingdang.java8;

import java.util.Comparator;
import java.util.function.BinaryOperator;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class ReduceTest {
	public static void main(String[] args) {
		IntStream stream=IntStream.of(1,2,3);
		//查看reduce的参数BinaryOperator
		int total=stream.reduce(0, (acc,element)->acc+element);
		//方法引用对应的一个Lambda表达式，也就是一个函数式接口，是惰性求值的。
		BinaryOperator<Long> i=Long::sum;
		System.out.println(i.apply(1l, 2l));
		//字符串中小写字母的个数
		String string="testasfasdf";
		string.chars().filter(Character::isLowerCase).count();
		
		//Comparator源码查看
		//strStream.max(Comparator.comparing(String::length));
		
		Stream<String> sStream=Stream.of("1","2","3");
		
		
	}
}

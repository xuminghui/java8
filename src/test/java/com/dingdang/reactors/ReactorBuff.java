package com.dingdang.reactors;

import java.time.Duration;

import org.junit.Test;

import com.google.common.eventbus.EventBus;

import reactor.core.publisher.Flux;
import reactor.core.publisher.UnicastProcessor;
import reactor.core.scheduler.Schedulers;

public class ReactorBuff {
	@Test
	public void testBuff1() {
		Flux.range(1, 100)
				.buffer(5)
				.subscribe(System.out::println);
	}

	@Test
	public void testBuff2() throws Exception {
		// Create a Flux that emits long values starting with 0 and incrementing
		// at specified time intervals on the global timer. If demand is not
		// produced in time, an onError will be signalled. The Flux will never
		// complete.
		Flux.interval(Duration.ofSeconds(1))
				.buffer(Duration.ofSeconds(5))
				.subscribe(System.out::println);
		Thread.sleep(1000000);
	}

	@Test
	public void testFilter() throws Exception {
		// 在caller线程执行，单元测试中可以看到完整输出
		Flux.range(1, 200)
				.filter(t -> t % 2 == 0)
				.publishOn(Schedulers.immediate())
				.subscribe(System.out::println);
		// 在新线程中执行，单元测试看不到完整输出
		Flux.range(1, 200)
				.filter(t -> t % 2 == 0)
				.publishOn(Schedulers.newSingle("new Thread"))
				.subscribe(System.out::println);
	}

	@Test
	public void testWindow() throws Exception {
		Flux.range(1, 200)
				.window(Duration.ofMillis(1))
				.subscribe(System.out::println);
		// TODO
		UnicastProcessor processor;
	}

	@Test
	public void testZipWith() throws Exception {
		Flux.just(1, 2, 3)
				.zipWith(Flux.just(3, 4))
				.subscribe(System.out::println);
	}

	@Test
	public void testTake() throws Exception {
		Flux.just(1, 2, 3)
				.takeWhile(t -> t == 1)
				.subscribe(System.out::println);
	}

	@Test
	public void testReduce() throws Exception {
		Flux.just(1, 2, 3)
				.reduce((x, y) -> x + y)
				.subscribe(System.out::println);
		Flux.just(1, 2, 3)
				.reduceWith(() -> 100, (x, y) -> x + y)
				.subscribe(System.out::println);
	}

	@Test
	public void testMerge() throws Exception {
		// merge 和 mergeSequential 操作符用来把多个流合并成一个 Flux 序列。不同之处在于 merge
		// 按照所有流中元素的实际产生顺序来合并，而 mergeSequential 则按照所有流被订阅的顺序，以流为单位进行合并。
		Flux.merge(Flux.interval(Duration.ofSeconds(1))
				.take(5),
				Flux.interval(Duration.ofSeconds(1))
						.take(5))
				.subscribe(System.out::println);
		Thread.sleep(1000000);

	}

	@Test
	public void testMergeSeq() throws Exception {
		// merge 和 mergeSequential 操作符用来把多个流合并成一个 Flux 序列。不同之处在于 merge
		// 按照所有流中元素的实际产生顺序来合并，而 mergeSequential 则按照所有流被订阅的顺序，以流为单位进行合并。
		Flux.mergeSequential(Flux.interval(Duration.ofSeconds(1))
				.take(5),
				Flux.interval(Duration.ofSeconds(1))
						.take(5))
				.subscribe(System.out::println);
		Thread.sleep(1000000);

	}

	@Test
	public void testFlatMap() throws Exception {
		Flux.just(1, 2,3,4)
				.flatMap(t -> Flux.interval(Duration.ofMillis(500))
						.take(t))
				.subscribe(System.out::println);
		Thread.sleep(1000000);
	}
	@Test
	public void testFlatMapSeq() throws Exception {
		Flux.just(1, 2,3,4)
				.flatMapSequential(t -> Flux.interval(Duration.ofMillis(500))
						.take(t))
				.subscribe(System.out::println);
		Thread.sleep(1000000);
	}
	
	//不同点 TODO
	@Test
	public void testConcatMap() throws Exception {
		Flux.just(1, 2,3,4)
				.concatMap(t -> Flux.interval(Duration.ofMillis(500))
						.take(t))
				.subscribe(System.out::println);
		Thread.sleep(1000000);
		EventBus a;
	}
	
	
}

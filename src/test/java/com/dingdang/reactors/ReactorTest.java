package com.dingdang.reactors;

import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import org.junit.Test;

import reactor.core.Disposable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Operators;

public class ReactorTest {
	private static List<String> words = Arrays.asList("the", "quick", "brown", "fox", "jumped", "over", "the", "lazy",
			"dog");

	@Test
	public void simpleCreation() {
		Flux<String> fewWords = Flux.just("Hello", "World");
		Flux<String> manyWords = Flux.fromIterable(words);

		fewWords.subscribe(System.out::println);
		System.out.println();
		manyWords.subscribe(System.out::println);
	}

	@Test
	public void shortCircuit() {
		Flux<String> helloPauseWorld = Mono.just("Hello")
				.concatWith(Mono.just("world")
						.delaySubscription(Duration.ofSeconds(5)));// world不会输出，因为主线程退出

		helloPauseWorld.subscribe(System.out::println);
	}

	@Test
	public void shortCircuit1() {
		Stream<String> helloPauseWorld = Mono.just("Hello")
				.concatWith(Mono.just("world")
						.delaySubscription(Duration.ofSeconds(5)))
				.toStream();// (or toIterable)

		helloPauseWorld.forEach(System.out::println);// block
	}

	@Test
	public void test3() {
		Mono<String> mono = Mono.just("Hello")
				.delaySubscription(Duration.ofMillis(460));
		Flux<String> flux = Flux.just("world")
				.delaySubscription(Duration.ofMillis(450));
		Flux.firstEmitting(mono, flux) // flux先于mono
				.toIterable()
				.forEach(System.out::println);
	}

	@Test
	public void simpleCreation1() throws Exception {
		Flux<String> manyWords = Flux.fromIterable(words)
				.flatMap(word -> Flux.fromArray(word.split("")))
				.mergeWith(Mono.just("s"))
				.distinct()
				.sort()
				.zipWith(Flux.range(1, Integer.MAX_VALUE), (string, count) -> String.format("%2d. %s", count, string))
				.delaySubscription(Duration.ofSeconds(4));
		manyWords.subscribe(System.out::println);
		Thread.sleep(5000);
	}

	@Test
	public void test() {
		List<Long> list = Arrays.asList(3l);
		Flux<Long> flux = Flux.fromIterable(list);
		Mono<Long> mono = flux.single(); // Flux与Mono的转换
		Flux<Long> fluxNew = mono.concatWith(Flux.just(10l, 20l));// 多个Mono合并成一个Flux
		fluxNew.subscribe(System.out::println);

	}

	@Test
	public void disposable() throws Exception {
		Flux<Integer> flux = Flux.range(1, Integer.MAX_VALUE);
		// return a reference to the subscription that one can use to cancel
		// said subscription when no more data is needed. Upon cancellation, the
		// source should stop producing values and clean up any resources it
		// created. This cancel and clean-up behavior is represented in Reactor
		// by the general-purpose Disposable interface.
		//在没有数据的情况下进行资源的释放?
		Disposable dis = flux.subscribe(System.out::println,e->{},()->{},s->{for(int i=0;i<12;i++)s.request(1000);});
		Thread.sleep(5000);
		System.out.println(dis.isDisposed());//仍然没有释放？理解问题

	}
}

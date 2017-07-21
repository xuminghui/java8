package com.dingdang.reactors;

import java.time.Duration;

import org.junit.Test;

import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

public class TestConnectableFlux {
	@Test
	public void test1() throws Exception {
		Flux<Long> source = Flux.interval(Duration.ofSeconds(1))
				.take(10)
				.publish()
				.autoConnect();
		source.subscribe();
		Thread.sleep(5000);
		source.toStream()
				.forEach(System.out::println);

	}

	// Here, we’ve introduced a sample() method with an interval of two seconds.
	// Now values will only be pushed to our subscriber every two seconds,
	// meaning the console will be a lot less hectic.
	// Of course, there’s multiple strategies to reduce the amount of data sent
	// downstream, such as windowing and buffering, but they will be left out of
	// scope for this article.
	@Test
	public void test2() throws Exception {
		ConnectableFlux<Object> publish = Flux.create(sink -> {
			while (true)
				sink.next(System.currentTimeMillis());
		})
				.sample(Duration.ofSeconds(2))
				.publish();
		publish.subscribe(System.out::println);
		publish.subscribe(System.out::println);

		publish.connect();
	}

	// 通过Log输出，可以看到执行线程在哪里
	// The Parallel scheduler will cause our subscription to be run on a
	// different thread, which we can prove by looking at the logs:
	@Test
	public void test3() throws Exception {
		Flux.just("1", "2", "3", "4")
				.log()
				.map(t -> {
					System.out.println(Thread.currentThread()
							.getName());
					return t + "2";
				})
				.publishOn(Schedulers.single())
				//.subscribeOn(Schedulers.parallel())
				.subscribe();
		Thread.sleep(50000);
	}

}

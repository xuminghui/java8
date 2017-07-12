package com.dingdang.reactors;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.reactivestreams.Subscription;

import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

public class ReactorCreate {
	interface MyEventListener<T> {
		void onDataChunk(List<T> chunk);

		void processComplete();
	}

	interface MyEventProcessor {
		void register(MyEventListener<String> eventListener);

		void dataChunk(String... values);

		void processComplete();
	}

	private MyEventProcessor processor = new MyEventProcessor() {
		private MyEventListener<String> eventListener;
		private ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();

		public void dataChunk(String... values) {
			executor.schedule(() -> eventListener.onDataChunk(Arrays.asList(values)), 500, TimeUnit.MILLISECONDS);
		};

		public void processComplete() {
			executor.schedule(() -> eventListener.processComplete(), 500, TimeUnit.MILLISECONDS);
		};

		public void register(MyEventListener<String> eventListener) {
			this.eventListener = eventListener;
		};
	};

	@Test
	public void testGenerate() throws Exception {
		Flux<String> bridge = Flux.create(sink -> {
			processor.register(new MyEventListener<String>() {

				@Override
				public void onDataChunk(List<String> chunk) {
					for (String s : chunk) {
						sink.next(s);
					}
				}

				@Override
				public void processComplete() {
					sink.complete();
				}

			});
		});
		bridge.publishOn(Schedulers.newSingle("threadPublishOn1"))
				.map(t -> {
					System.out.println("map1" + Thread.currentThread()
							.getName());
					return t;
				})
				.publishOn(Schedulers.newSingle("threadPublishOn2"))

				.map(t -> {
					System.out.println("map2" + Thread.currentThread()
							.getName());
					return t;
				})
				.subscribeOn(Schedulers.newSingle("threadSubscribeOn"))
				.subscribe(t -> {
					System.out.println(Thread.currentThread()
							.getName());
				});
		processor.dataChunk("1", "3");
		Thread.sleep(10000l);
		/*
		 * StepVerifier.withVirtualTime(() -> bridge) .expectSubscription()
		 * .expectNoEvent(Duration.ofSeconds(10)) .then(() ->
		 * processor.dataChunk("1", "2", "3")) .expectNext("1", "2", "3")
		 * .expectNoEvent(Duration.ofSeconds(10)) .then(() ->
		 * processor.processComplete()) .verifyComplete();
		 */
	}

	@Test
	public void testMono() {
		// Create a Mono that terminates with the specified error immediately
		// after being subscribed to.
		Mono<Object> mono = Mono.error(new NullPointerException());
		Mono.delay(Duration.ofDays(1));
	}

	@Test
	public void testSubscriberManyTimes() {
		//
		Flux<String> flux = Flux.just("1", "2", "3");
		flux.subscribe(System.out::println);
		flux.subscribe(System.out::println);
	}

	@Test
	public void testSubscription() throws Exception {
		Flux<String> source = Flux.just("1", "2", "3");
		source.subscribeOn(Schedulers.newSingle("subscribeOn"))
				.map(String::toUpperCase)

				.publishOn(Schedulers.newSingle("publishOn"))// 加入此段代码后，直接就在publishOn指定的上下文中执行了！

				.subscribe(new BaseSubscriber<String>() {
					@Override
					protected void hookOnSubscribe(Subscription subscription) {
						System.out.println("onsubscribe:" + Thread.currentThread()
								.getName());
						request(1);
					}

					@Override
					protected void hookOnNext(String value) {
						System.out.println(Thread.currentThread()
								.getName());
						request(1);
					}

				});

		Thread.sleep(1000000000l);
	}
	
	//通过using 从资源中获取数据数据
	@Test
	public void testDefer() {
		Flux.defer(() -> Flux.just(1, 2, 3)
				.doOnSubscribe(t -> {
					System.out.println(t);
				})).subscribe();
		
		Flux.using(()->{
			return Paths.get("D:\\jd.txt");
		}, r->{
			List<String> lines;
			try {
				lines = Files.readAllLines(r, StandardCharsets.UTF_8);
				return Flux.fromIterable(lines);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
			
		}, r->{
			System.out.println(r);
		}).subscribe(System.out::println);
	}

}

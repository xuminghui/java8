package com.dingdang.reactors;

import java.util.concurrent.Executors;
import java.util.function.Function;

import org.junit.Test;
import org.reactivestreams.Subscription;

import reactor.core.publisher.BaseSubscriber;
import reactor.core.publisher.Flux;
import reactor.core.publisher.SignalType;
import reactor.core.publisher.SynchronousSink;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

public class ReactorGenerate {

	@Test
	public void testGenerate() {
		Flux<String> flux = Flux.generate(() -> 0, (state, sink) -> {
			sink.next("3 x " + state + " = " + 3 * state);
			if (state == 10)
				sink.complete();
			return state + 1;
		});
		flux.subscribe(new BaseSubscriber<String>() {
			// Implement this method to call request(long) as an initial request
			// 初始请求
			@Override
			protected void hookOnSubscribe(Subscription subscription) {
				request(2);
			}

			@Override
			protected void hookOnNext(String value) {
				System.out.println("1:" + value + "end");
				if (value.equals("3 x 5 = 15")) {
					cancel(); // 触发cancel事件
					return;
				}
				if (value.equals("3 x 6 = 18")) {
					throw new RuntimeException("eeeeeeeeeee"); // 抛出异常，触发error执行
				}
				request(2);
			}

			protected void hookOnComplete() {
				System.out.println("complete");
			};

			protected void hookOnCancel() {
				System.out.println("cancel");
			};

			protected void hookOnError(Throwable throwable) {
				System.out.println("error");
				throwable.printStackTrace();
			};

			protected void hookFinally(SignalType type) {
				System.out.println(type.name());
			};
		});
	}

	@Test
	public void test() {
		// Add behavior (side-effect) triggered when the Flux is subscribed.
		Flux.just("1", "2", "3")
				.doOnSubscribe(System.out::println);
	}

	@Test
	public void testTransForm() {
		Function<Flux<String>, Flux<String>> trans = t -> t.filter(name -> name.equals("1"))
				.map(m -> m + "111111111");
		Flux.just("1", "2", "3")
				.transform(trans)
				.doOnNext(System.out::println)
				.subscribe();
	}

	@Test
	public void testPublishingOnForSingleThread() {
		// sink就是同步的生成
		Flux.generate(() -> 0, (state, sink) -> {
			sink.next(state);
			if (state == 10)
				sink.complete();
			return state + 1;
		})
				.publishOn(Schedulers.single())
				.map(m->m)
				.doOnNext(System.out::println)
				.subscribe();
	}

	@Test
	public void testPublishingOn() throws Exception {
		Scheduler scheduler = Schedulers.fromExecutor(Executors.newFixedThreadPool(10));
		Flux.just("1","2","3","4")
				.publishOn(scheduler)
				.map(m -> {
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					System.out.println(Thread.currentThread()
							.getName()+":map" + m);
					return m;
				})
				.subscribe(t -> {
					System.out.println(Thread.currentThread() //这个是onNext触发后，调用consumer的操作，打印的就是触发onNext的线程得名字
							.getName() + ":" + t);
				});

		Thread.sleep(50000000l);
	}
	@Test
	public void test11() throws Exception {
		SynchronousSink a;
	}
}

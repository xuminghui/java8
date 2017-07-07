package com.dingdang.reactors;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

import reactor.core.publisher.Flux;
import reactor.core.scheduler.Scheduler;
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

		Executors.newFixedThreadPool(1)
				.submit(() -> {
					for (;;) {
						processor.dataChunk("1", "2", "3");
					}

				});
		Scheduler scheduler = Schedulers.fromExecutor(Executors.newFixedThreadPool(10));
		bridge.publishOn(scheduler)
				.subscribe(t->{
					System.out.println(Thread.currentThread().getName()+": "+t);
				});
		for (;;) {

		}

	}

}

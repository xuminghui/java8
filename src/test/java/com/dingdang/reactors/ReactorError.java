package com.dingdang.reactors;

import org.junit.Test;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.SignalType;
import reactor.test.StepVerifier;

public class ReactorError {
	@Test
	public void testError() {
		Flux<String> flux = Flux.just("123")
				.map(t -> {
					return "1" + 1 / 0;
				})
				.onErrorReturn("error");
		StepVerifier.create(flux)
				.expectNext("error")
				.verifyComplete();
	}

	@Test
	public void testflatMap() throws Exception {
		Flux.just("a,b,c,d")
				.flatMap(t -> callService().onErrorMap(e -> new Exception(e)))
				.doOnError(e -> System.out.println(e.getMessage()))
				.subscribe(System.out::println);
		Thread.sleep(10000l);

	}

	public Flux<String> callService() {
		throw new NullPointerException("error");
	}

	// 分别为每一个subscriber产生一个compose,在调用subscribe的时候，不执行compose操作
	@Test
	public void testCompose() {
		Flux.just("1", "2")
				.compose(t -> {
					System.out.println(t);
					return Mono.from(t);
				});
				//.subscribe(System.out::println);
	}

	// 在装备过程中转换一次，也就是说不执行subscribe的时候，就已经进行了转换
	@Test
	public void testTransform() {
		Flux.just("1", "2")
				.transform(t -> {
					System.out.println(t);
					return Mono.from(t);
				});
	}

}

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
import reactor.test.StepVerifier;

public class StepVeriferTest {
	@Test
	public void test() throws Exception {
		Flux<String> flux=Flux.just("1","2");
		StepVerifier.withVirtualTime(()->Mono.delay(Duration.ofDays(1)));
	}
}

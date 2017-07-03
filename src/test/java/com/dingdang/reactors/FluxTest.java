package com.dingdang.reactors;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Duration;

import org.junit.Test;
import org.reactivestreams.Publisher;

import com.dingdang.service.reactor.ReactorService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Operators;
import reactor.test.StepVerifier;

public class FluxTest {
	@Test(expected = NullPointerException.class)
	public void arrayNull() {
		Flux.fromArray((Integer[]) null);
	}

	@Test
	public void normal() {
		ReactorService rs = new ReactorService();
		StepVerifier.create(rs.toFlux("a"))
				.expectNext("a")
				.expectComplete()
				.verify();
	}

	@Test
	public void normal1() {
		ReactorService rs = new ReactorService();
		StepVerifier.create(rs.toFluxMany("a", "b", "c", "d", "e"))
				.consumeNextWith(c -> assertThat(c).as("first") // 添加error注释
						.matches("[a-z]"))
				.consumeNextWith(c -> assertThat(c).as("second")
						.matches("[a-z]"))
				.consumeNextWith(c -> assertThat(c).as("third")
						.matches("[a-z]"))
				.expectComplete()
				.verify();
	}

	@Test
	public void normal2() {
		ReactorService rs = new ReactorService();
		StepVerifier.withVirtualTime(() -> rs.withDelay("test"))
				.expectSubscription()
				.thenAwait(Duration.ofSeconds(90))
				.expectNoEvent(Duration.ofSeconds(10))
				.expectNext("test")
				.expectComplete()
				.verify();
	}
	@Test
	public void normal3() { 
		//lambda表示一个Publisher(入参是Subscriber,无返回值)
		StepVerifier.create(Flux.from(s -> {
			s.onSubscribe(Operators.emptySubscription());
			s.onNext("foo");
			s.onComplete();
			s.onNext("bar");
			s.onNext("baz");
		}).take(3))
        .expectNext("foo")
        .expectComplete()
        .verifyThenAssertThat()
        .hasDroppedElements()
        .hasDropped("baz")
        .hasDroppedExactly("baz", "bar");
	}
	
	
}

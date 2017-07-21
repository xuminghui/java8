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
import reactor.test.publisher.TestPublisher;
import reactor.test.publisher.TestPublisher.Violation;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

public class TestPublisherTest {
	@Test
	public void test1() throws Exception {
		TestPublisher<String> publisher = TestPublisher.create();
		assertThatExceptionOfType(NullPointerException.class).isThrownBy(() -> publisher.next(null))
				.withMessage("emitted values must be non-null");
	}

	@Test
	public void test2() throws Exception {
		TestPublisher<String> publisher = TestPublisher.createNoncompliant(Violation.ALLOW_NULL);
		StepVerifier.create(publisher)
				.then(() -> publisher.emit("next", null))
				.expectNext("next", null)
				.expectComplete()
				.verify();
	}
	@Test
	public void test3() throws Exception {
		
	}
}

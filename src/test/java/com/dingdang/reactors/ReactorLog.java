package com.dingdang.reactors;

import org.junit.Test;

import reactor.core.publisher.Flux;

public class ReactorLog {
	//log用法，记录事件的日志
	@Test
	public void testDefer() {
		Flux.just("1","2","3").log();
	}

}

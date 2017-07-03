package com.dingdang.service.reactor;

import java.time.Duration;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Operators;

public class ReactorService {
	public Flux<String> toFlux(String param){
		Operators.emptySubscription();
		return Flux.just(param);
	}
	public Flux<String> toFluxMany(String...param){
		return Flux.just(param).take(Math.min(param.length, 3));
	}
	
	public Flux<String> withDelay(String param){
		return Flux.just(param).delaySubscription(Duration.ofSeconds(100));
	}
}

package com.dingdang.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.Data;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Controller
public class ExampleController {

	@RequestMapping("hello/{who}")
	@ResponseBody
	public Mono<String> hello(@PathVariable String who) {
		return Mono.just(who)
				.map(w -> "Hello " + w + "!");
	}

	@RequestMapping(value = "heyMister", method = RequestMethod.POST)
	@ResponseBody
	public Flux<String> hey(@RequestBody Mono<Sir> body) {
		return Mono.just("Hey mister ")
				.concatWith(Mono.just(". how are you?"));
	}

	@Data
	public static class Sir {
		private String firstName;
		private String lastName;
	}
}
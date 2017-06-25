package com.dingdang.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyExtractors;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import com.dingdang.dao.UserRepository;
import com.dingdang.domain.UserForFlux;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class UserHandler {
	@Autowired
	private UserRepository userRepository;

	public Mono<ServerResponse> handleGetUsers(ServerRequest request) {
		return ServerResponse.ok()
				.body(userRepository.getUsers(), UserForFlux.class);
	}
	public Mono<ServerResponse> handleRequestUsers(ServerRequest request) {
		Flux<UserForFlux> users=request.body(BodyExtractors.toFlux(UserForFlux.class));
		return ServerResponse.ok()
				.body(users, UserForFlux.class);
	}
	public Mono<ServerResponse> handleRequestToString(ServerRequest request) {
		Mono<String> users=request.body(BodyExtractors.toMono(String.class));
		return ServerResponse.ok()
				.body(users, String.class);
	}

	public Mono<ServerResponse> handleGetUserById(ServerRequest request) {
		return userRepository.getUserById(request.pathVariable("id"))
				.flatMap(user -> ServerResponse.ok()
						.body(Mono.just(user), UserForFlux.class))
				.switchIfEmpty(ServerResponse.notFound()
						.build());
	}
}
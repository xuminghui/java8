package com.dingdang.dao;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Repository;

import com.dingdang.domain.UserForFlux;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Repository
public class UserRepository {
	private final List<UserForFlux> users = new ArrayList<>();
	@PostConstruct
	public void init(){
		users.add(new UserForFlux(1L, "User1"));
		users.add(new UserForFlux(2L, "User2"));
	}
	// Mono 和 Flux 是 Reactive streams 的发布者实现
	// Mono 是 0 或者任意单个值的发布
	public Mono<UserForFlux> getUserById(String id) {
		return Mono.justOrEmpty(users.stream()
				.filter(user -> {
					return user.getId()
							.equals(Long.valueOf(id));
				})
				.findFirst()
				.orElse(null));
	}

	// Flux 是 0 到任意值的发布
	public Flux<UserForFlux> getUsers() {
		return Flux.fromIterable(users);
	}

	public Mono<Void> save(Mono<UserForFlux> user) {
		System.out.println(users);
		users.add(user.block());
		return Mono.empty();
	}

}
package com.dingdang.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.server.ServerRequest;

import com.dingdang.dao.UserRepository;
import com.dingdang.domain.UserForFlux;

import reactor.core.publisher.Mono;

@RestController
public class PersonController {

	private UserRepository userDao;

	public PersonController(UserRepository userDao) {
		this.userDao = userDao;
	}

	@GetMapping("/person/{id}")
	Mono<UserForFlux> findById(@PathVariable String id) {
		return userDao.getUserById(id);
	}

	@PostMapping("/save/person")
	Mono<Void> create(@RequestBody UserForFlux user) {
		System.out.println("2222222222222222222222222"+user);
		return this.userDao.save(Mono.just(user))
				.then();
	}

}
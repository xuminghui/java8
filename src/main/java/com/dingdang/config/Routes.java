package com.dingdang.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RouterFunction;

import com.dingdang.service.UserHandler;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RequestPredicates.POST;
import static org.springframework.web.reactive.function.server.RequestPredicates.contentType;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

@Configuration
public class Routes {
	private UserHandler userHandler;

	public Routes(UserHandler userHandler) {
		this.userHandler = userHandler;
	}

	@Bean
	public RouterFunction<?> routerFunction() {
		return route(GET("/api/user").and(accept(MediaType.APPLICATION_JSON)), userHandler::handleGetUsers).and(
				route(GET("/api/user/{id}").and(accept(MediaType.APPLICATION_JSON)), userHandler::handleGetUserById))
				.and(route(POST("/api/post/user").and(contentType(MediaType.APPLICATION_JSON)),userHandler::handleRequestUsers))
				.and(route(POST("/api/post/string").and(contentType(MediaType.APPLICATION_JSON)),userHandler::handleRequestToString));
	}
}
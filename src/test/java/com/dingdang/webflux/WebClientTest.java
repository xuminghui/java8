package com.dingdang.webflux;

import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;

import com.dingdang.domain.UserForFlux;

public class WebClientTest {
	// 服务启动后，直接通过webclient访问 相当于RestTemplate
	public static void main(String[] args) throws Exception {
		WebClient client = WebClient.create("http://localhost:8080");
		ClientResponse response=client.post()
				.uri("/save/person")
				.accept(MediaType.APPLICATION_JSON)
				.syncBody(new UserForFlux(10l, "123"))
				.exchange()
				.block();
		System.out.println(response.statusCode());

		/*
		 * client.get() .uri("person/{id}", 10l) .exchange() .flatMap(response
		 * -> response.bodyToMono(UserForFlux.class))
		 * .subscribe(System.out::println);
		 */
		Thread.sleep(1000000l);

	}
}

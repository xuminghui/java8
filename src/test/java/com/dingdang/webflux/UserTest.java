package com.dingdang.webflux;

import java.io.IOException;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.FluxExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.dingdang.domain.UserForFlux;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.DEFINED_PORT)
public class UserTest {
	@Autowired
	private WebTestClient webTestClient;

	@Test
	public void test() throws IOException {
		FluxExchangeResult<UserForFlux> result = webTestClient.get()
				.uri("/api/user")
				.accept(MediaType.APPLICATION_JSON)
				.exchange()
				.returnResult(UserForFlux.class);
		assert result.getStatus()
				.value() == 200;
		List<UserForFlux> users = result.getResponseBody()
				.collectList()
				.block();
		assert users.size() == 2;
		assert users.iterator()
				.next()
				.getUser()
				.equals("User1");
	}

	@Test
	public void test1() throws IOException {
		UserForFlux user = webTestClient.get()
				.uri("/api/user/1")
				.accept(MediaType.APPLICATION_JSON)
				.exchange()
				.returnResult(UserForFlux.class)
				.getResponseBody()
				.blockFirst();
		assert user.getId() == 1;
		assert user.getUser()
				.equals("User1");
	}

	@Test
	public void test2() throws IOException {
		webTestClient.get()
				.uri("/api/user/10")
				.accept(MediaType.APPLICATION_JSON)
				.exchange()
				.expectStatus()
				.isNotFound();
	}
	@Test
	public void testPostUser() throws Exception {
		UserForFlux user = new UserForFlux(1l, "user");
		ObjectMapper om = new ObjectMapper();
		String json = om.writeValueAsString(user);
		List<UserForFlux> result = webTestClient.post()
				.uri("/api/post/user")
				.contentType(MediaType.APPLICATION_JSON)
				.syncBody(json)
				.exchange()
				.returnResult(UserForFlux.class)
				.getResponseBody()
				.collectList()
				.block();
		assert result.size()==1:"error";
		
	}
	@Test
	public void testPostString() throws Exception {
		String testString="hello world!";
		String result = webTestClient.post()
				.uri("/api/post/string")
				.contentType(MediaType.APPLICATION_JSON)
				.syncBody(testString)
				.exchange()
				.returnResult(String.class)
				.getResponseBody().blockFirst();
		assert result.equals(testString):"error";
		
	}
}
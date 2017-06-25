package com.dingdang.domain;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserForFlux {

	public UserForFlux(Long id, String user) {
		this.id = id;
		this.user = user;
	}

	private Long id;
	private String user;
}
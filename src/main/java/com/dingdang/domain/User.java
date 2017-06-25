package com.dingdang.domain;

import lombok.Data;

@Data
public class User {
	private int id;
	private String name;
	private int age;
	private UserEnum type;
	public static enum UserEnum{
		ADMIN(0),NORMAL(1);
		private int value;
		private UserEnum(int value){
			this.value=value;
		}
	}
}

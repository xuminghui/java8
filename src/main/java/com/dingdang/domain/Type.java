package com.dingdang.domain;

public enum Type {
	ADMIN(0),NORMAL(1);
	private int value;
	private Type(int value){
		this.value=value;
	}
	
}

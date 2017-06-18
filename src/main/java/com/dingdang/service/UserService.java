package com.dingdang.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dingdang.dao.UserDao;
import com.dingdang.domain.User;

@Service
public class UserService {
	
	@Autowired
	private UserDao  userDao;
	public String sayHello(String name){
		return "hello, "+name;
	}
	public List<User> getUsers(int age,String name){
		return userDao.get(age,name);
	}
	public List<User> getUsers(Map<String,Object> map){
		return userDao.getMap(map);
	}
}

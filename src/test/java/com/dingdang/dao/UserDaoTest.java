package com.dingdang.dao;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.dingdang.Application;
import com.dingdang.domain.User;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)  
public class UserDaoTest {
	@Autowired
	private UserDao dao;
	@Test
	public void testInsert(){
		List<User> user=dao.get(11, "name");
		System.out.println(user.size());
		System.out.println(user.get(0).getType());
	}
}

package com.dingdang.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.dingdang.Application;

@RunWith(SpringRunner.class)  
//@WebMvcTest(UserController.class)//单纯测试controller的注解，对于@Component会忽略
@AutoConfigureMockMvc //基于MockMvc的测试用例
@SpringBootTest(classes=Application.class) //spring boot 启动类
public class UserControllerTest {  
	@Autowired
    private MockMvc mvc;
	//@MockBean
	//private UserService service;
   /* @Test  
    public void testShow() throws Exception {  
    	given(this.service.sayHello("john"))
        .willReturn("hello,john");
    	this.mvc.perform(get("/user/1/name").accept(MediaType.TEXT_PLAIN))
        .andExpect(status().isOk()).andExpect(content().string("name"));
    }*/
    
    @Test  
    public void testUserList() throws Exception {  
    	MvcResult result=this.mvc.perform(get("/user/list/10/name").accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk()).andReturn();
    	System.out.println(result.getResponse().getContentAsString());
    }
    @Test  
    public void testUserListMap() throws Exception {  
    	MvcResult result=this.mvc.perform(get("/user/listMap/10/name").accept(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk()).andReturn();
    	System.out.println(result.getResponse().getContentAsString());
    }
    
  
    
  
}  
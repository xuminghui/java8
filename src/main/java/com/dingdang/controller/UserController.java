package com.dingdang.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import com.dingdang.domain.User;
import com.dingdang.service.UserService;

@RestController  
@RequestMapping("/user")  
public class UserController {  
	
	@Autowired
	private UserService userService;
	@RequestMapping("/async/{id}")
	public DeferredResult<User> viewAsync(@PathVariable("id") int id) {  
		DeferredResult<User> deferredResult = new DeferredResult<User>();
		System.out.println("/asynctask 调用！thread id is : " + Thread.currentThread().getId());
		Executors.newFixedThreadPool(1).submit(()->{
			try {
				Thread.sleep(500000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("异步调用执行完成, thread id is : " + Thread.currentThread().getId());
			User user = new User();  
	        user.setId(id);  
	        user.setName("zhang");  
			deferredResult.setResult(user);
		});
        return deferredResult;  
    }  
    @RequestMapping("/{id}")  
    public User view(@PathVariable("id") int id) {  
    	try {
			Thread.sleep(500000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        User user = new User();  
        user.setId(id);  
        user.setName("zhang");  
        return user;  
    }  
    @RequestMapping("/{id}/name")  
    public String getName(@PathVariable("id") Long id) {  
        return "name";  
    }  
  
 /*   @RequestMapping("/list/{age}/{name}")  
    public PageInfo<User> getUserList(@PathVariable int age,@PathVariable String name) { 
    	PageHelper.startPage(0, 2);
    	List<User> users=userService.getUsers(age,name);
    	System.out.println(users.size());
        return new PageInfo<User>(users);
    }  
    @RequestMapping("/listMap/{age}/{name}")  
    public PageInfo<User> getUserListByMap(@PathVariable int age,@PathVariable String name) { 
    	Map<String,Object> map=new HashMap<>();
    	map.put("age", 10);
    	map.put("name", name);
    	PageHelper.startPage(0, 2);
    	List<User> users=userService.getUsers(map);
    	System.out.println(users.size());
        return new PageInfo<User>(users);
    }  */
    @RequestMapping("/listMap/{age}/{name}")  
    public List<User> getUserListByMap(@PathVariable int age,@PathVariable String name) { 
    	Map<String,Object> map=new HashMap<>();
    	map.put("age", 10);
    	List<User> users=userService.getUsers(map);
    	return users;
    }
}  
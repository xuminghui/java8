package com.dingdang.json;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import com.dingdang.domain.Child;
import com.dingdang.domain.Log;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;

@RunWith(SpringRunner.class)

public class JsonTest {
	@Test
	public void jsonCreator() throws Exception{
		ObjectMapper objectMapper = new ObjectMapper(); 
		//objectMapper.configure(SerializationFeature.WRITE_ENUMS_USING_INDEX, true);
		//objectMapper.configure(DeserializationFeature.READ_ENUMS_USING_TO_STRING,true );
	    String jsonStr = "{\"id\":123,\"name\":\"myName\",\"desc\":\"desc\",\"created_time\":123456789,\"type\":\"ADMIN\"}";
	    Log log=objectMapper.readValue(jsonStr, Log.class);
	    Assert.assertEquals("myName",log.getName()); 
	    System.out.println(log.getCreatedTime());
	    System.out.println(log.getType());
	    System.out.println(log.getId());
	}
	//解决循环引用，是循环引用自身的时候，用Id表示
	@Test
	public void jsonJsonIdentityInfo() throws Exception{
		ObjectMapper objectMapper = new ObjectMapper();
		Log log=new Log();
		Child child=new Child();
		child.setLog(log);
		log.setChildren(Lists.newArrayList(child));
		log.setId(345);
		String logJson=objectMapper.writeValueAsString(log);
		System.out.println(logJson);
		
		String childJson=objectMapper.writeValueAsString(child);
		System.out.println(childJson);
		
	}
}

package com.dingdang.kafka;

import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import com.dingdang.Application;
import com.dingdang.kakfa.Listener;
@RunWith(SpringRunner.class)  
@SpringBootTest(classes=Application.class)
public class KafkaTest {
	@Autowired
	private Listener listener;

	@Autowired
	private KafkaTemplate<Integer, String> template;

	@Test
	public void testSimple() throws Exception {
	    template.send("test", "12345111");
	    template.flush();
	    Assert.assertTrue(this.listener.latch1.await(100, TimeUnit.SECONDS));
	}
}

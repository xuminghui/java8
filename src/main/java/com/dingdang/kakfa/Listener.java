package com.dingdang.kakfa;

import java.util.concurrent.CountDownLatch;

import org.springframework.kafka.annotation.KafkaListener;

public class Listener {

    public final CountDownLatch latch1 = new CountDownLatch(1);

    @KafkaListener(id = "foo", topics = "test")
    public void listen1(String foo) {
    	System.out.println(foo);
        this.latch1.countDown();
    }
    

}
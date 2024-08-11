package com.cheng.springcloudstream.consumer;

import com.cheng.springcloudstream.config.RabbitMqMessageChannel;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.stereotype.Component;

@Component
public class MsgReceiver {

    @StreamListener(RabbitMqMessageChannel.INPUT)
    public void receive(String msg) {
        System.out.println("receive msg: " + msg);
    }
}

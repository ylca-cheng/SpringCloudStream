package com.cheng.springcloudstream.config;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;

public interface RabbitMqMessageChannel {

    String INPUT = "rabbitMQInput";

    String OUTPUT = "rabbitMQOutput";

    /**
     * 接收消息
     */
    @Input(INPUT)
    MessageChannel messageInput();

    @Output(OUTPUT)
    MessageChannel messageOutput();

}

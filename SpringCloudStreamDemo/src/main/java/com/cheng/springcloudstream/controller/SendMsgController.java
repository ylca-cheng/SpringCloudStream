package com.cheng.springcloudstream.controller;

import com.cheng.springcloudstream.config.RabbitMqMessageChannel;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/send")
public class SendMsgController {

    private final RabbitMqMessageChannel rabbitMqMessageChannel;

    /**
     * 发送消息
     */
    @RequestMapping("/msg")
    public String sendMsg(String msg) {
        Message<String> build = MessageBuilder.withPayload(msg).build();
        rabbitMqMessageChannel.messageOutput().send(build);
        return "success send msg " + msg;
    }
}

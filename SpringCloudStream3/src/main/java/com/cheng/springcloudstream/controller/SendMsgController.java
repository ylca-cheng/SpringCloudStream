package com.cheng.springcloudstream.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/send")
public class SendMsgController {

    private final StreamBridge streamBridge;

    /**
     * 发送消息
     */
    @RequestMapping("/msg")
    public String sendMsg(String msg) {
        streamBridge.send("myOutput", MessageBuilder.withPayload(msg).build());
        return "success send msg " + msg;
    }
}

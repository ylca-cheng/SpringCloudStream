package com.cheng.springcloudstream;

import com.cheng.springcloudstream.config.RabbitMqMessageChannel;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.cloud.stream.messaging.Source;

@EnableBinding({RabbitMqMessageChannel.class, Source.class, Sink.class})
@SpringBootApplication
public class SpringCloudStreamDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudStreamDemoApplication.class, args);
    }

}

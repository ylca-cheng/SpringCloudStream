package com.cheng.springcloudstream.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

@Configuration
public class StreamConfig {

    @Bean
    public Supplier<String> source() {
        return () -> {
            String source ="from source";
            System.out.println("=============from source===============");
            return source;
        };
    }

    @Bean
    public Function<String,String> transfer() {
        return msg -> {
            System.out.println("===========transfer msg "+msg+"===========");
            return "transfer msg: " + msg;
        };
    }

    @Bean
    public Consumer<String> sink() {
        return msg -> {
            System.out.println("=============sink msg: " + msg + "===============");
        };
    }
}

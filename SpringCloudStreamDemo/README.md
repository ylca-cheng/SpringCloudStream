# Spring Cloud Stream

## 一、使用步骤

### 1. 添加依赖
```xml
<!-- 对接RabbitMQ -->
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-stream-rabbit</artifactId>
</dependency>
```
```xml
<!-- 对接Kafka -->
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-stream-kafka</artifactId>
</dependency>
```
```xml
<!-- 对接RocketMQ -->
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-stream-rocketmq</artifactId>
</dependency>
```

### 2. 配置文件
```yaml
spring:
  cloud:
    stream:
      bindings:
        # 消费者 可以自定义，例如 rabbitmqInput
        input:
          destination: test-topic
          group: test-group
          content-type: application/json
        # 生产者 可以自定义，例如 rabbitmqOutput
        output:
          destination: test-topic
          content-type: application/json
```
### 3. 启用绑定
```java
@EnableBinding({Source.class, Sink.class})
```

### 4. 生产者
```java
@Autowired
private Source source;

public void send(String message) {
    source.output().send(MessageBuilder.withPayload(message).build());
}
```

### 5. 消费者
```java
@StreamListener(Sink.INPUT)
public void receive(String message) {
    log.info("Received: " + message);
}
```

## 二、Spring Cloud Stream 三大组件

### 1. Binder
> Binder 是 Spring Cloud Stream 的核心组件，它负责与消息中间件进行交互。Spring Cloud Stream 提供了对 RabbitMQ、Kafka、RocketMQ 等消息中间件的支持。

Spring Cloud Stream 提供了三种 Binder(内置的)：
- RabbitMQ
- Kafka
- RocketMQ

Spring Cloud Stream 还支持自定义 Binder，只需要配置 Binder 即可。
```yml
spring:
  cloud:
    stream:
      binders:
        testRabbit:
         type: rabbit
         environment:
          spring:
            rabbitmq:
              host: localhost
              port: 5672
              username: guest
              password: guest
              virtual-host: /
```

并且可以指定默认binder，不指定默认的binder，就是框架内置的默认binder。
```yml
spring:
  cloud:
    stream:
      default-binder: testRabbit
```

### 2. Binding
> Binding 是应用程序与 Binder 之间的桥梁，它负责将消息通道与目的地进行绑定。Binding 由 Binder 创建，应用程序通过 Binding 与 Binder 进行交互。

Spring Cloud Stream 提供了三种 Binding(内置的)：
- Source：输出消息通道
- Sink：输入消息通道
- Processor：输入输出消息通道

如果需要自定义 Binding，可以参考以上三个自定义接口，并使用注解 `@EnableBinding` 来启用绑定。

```java
import org.springframework.cloud.stream.annotation.EnableBinding;

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
// 在启动类上添加注解
@EnableBinding({RabbitMqMessageChannel.class})
```

对应配置文件如下：
```yaml
spring:
  cloud:
    stream:
      bindings:
        rabbitMQInput:
          destination: test-topic
          group: test-group
          content-type: application/json
        rabbitMQOutput:
          destination: test-topic
          content-type: application/json
```

使用自定义的binding，发送接收消息：
```java
@Autowired
private RabbitMqMessageChannel rabbitMqMessageChannel;

// 发送消息
public void send(String message) {
    rabbitMqMessageChannel.messageOutput().send(MessageBuilder.withPayload(message).build());
}

// 接收消息
@StreamListener(RabbitMqMessageChannel.INPUT)
public void receive(String message) {
    log.info("Received: " + message);
}
```

### 3. Message
> Message 是消息的载体，它包含了消息的内容和元数据。Spring Cloud Stream 使用 Spring Messaging 来处理消息，Message 是 Spring Messaging 的核心组件。

## 三、Spring Cloud Stream 高级特性
## 1、个性化配置使用
[参考文档链接](https://docs.spring.io/spring-cloud-stream/docs/current/reference/html/spring-cloud-stream-binder-rabbit.html#_configuration_options)

使用方式：
```yml
spring.cloud.stream.rabbit.bindings.<channelName>.consumer..
```

例如：
```yaml
spring:
  cloud:
    stream:
      rabbit:
        bindings:
          input:
            consumer:
              # 自动创建队列
              bind-queue: true
```
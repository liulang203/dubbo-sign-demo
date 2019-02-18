package com.ddnet.dubbo;

import com.ddnet.dubbo.pojo.Message;
import com.ddnet.dubbo.service.HelloService;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

/**
 * Created by Vinson.Ding on 2019/2/18.
 */
@SpringBootApplication
@RestController
public class HelloClientApplication {
    @Reference(url = "dubbo://127.0.0.1:12345")
    private HelloService helloService;

    @RequestMapping("/")
    public Message mainPage() {
        Message message = new Message("word","hello", LocalDateTime.now());
        Message receiveMsg = helloService.sayHello("jason",message);
        return receiveMsg;
    }

    public static void main(String[] args) {
        SpringApplication.run(HelloClientApplication.class, args);
    }
}

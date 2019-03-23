package com.ddnet.dubbo;

import com.ddnet.dubbo.pojo.Message;
import com.ddnet.dubbo.service.HelloService;
import org.apache.dubbo.common.Constants;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.dubbo.rpc.RpcContext;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.Random;

/**
 * Created by Vinson.Ding on 2019/2/18.
 */
@SpringBootApplication
@RestController
public class HelloClientApplication {
    @Reference
    private HelloService helloService;

    @RequestMapping("/")
    public Message mainPage(@RequestParam("tag") String tag) {
        Message message = new Message("word", "hello", LocalDateTime.now(), new Random().nextInt(89283293));
        if ("server1".equals(tag) || "server2".equals(tag)) {
            RpcContext.getContext().setAttachment(Constants.TAG_KEY, tag);
        }
        Message receiveMsg = helloService.sayHello("jason", message);
        return receiveMsg;
    }

    public static void main(String[] args) {
        SpringApplication.run(HelloClientApplication.class, args);
    }
}

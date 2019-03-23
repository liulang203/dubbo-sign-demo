package com.ddnet.dubbo.service;

import com.ddnet.dubbo.pojo.Message;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDateTime;

/**
 * Created by Vinson.Ding on 2019/2/18.
 */
@Service
@Slf4j
public class HelloServiceImpl implements HelloService {
    @Value("${spring.application.name}")
    private String serviceName;
    @Override
    public Message sayHello(String from, Message message) {
        log.debug("recieve from [{}] message [{}]", from, message);
        message.setTo(from);
        message.setMessage("from="+serviceName+", messageBody="+message.getMessage());
        message.setSendTime(LocalDateTime.now());
        return message;
    }
}

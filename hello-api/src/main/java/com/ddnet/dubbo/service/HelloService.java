package com.ddnet.dubbo.service;

import com.ddnet.dubbo.pojo.Message;

/**
 * Created by Vinson.Ding on 2019/2/18.
 */
public interface HelloService {
    /**
     * 发送hello
     * @param from from
     * @param message message
     * @return message
     */
    Message sayHello(String from,Message message);
}

package com.ddnet.dubbo.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Created by Vinson.Ding on 2019/2/18.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Message implements Serializable {
    private String to;
    private String message;
    private LocalDateTime sendTime;
}

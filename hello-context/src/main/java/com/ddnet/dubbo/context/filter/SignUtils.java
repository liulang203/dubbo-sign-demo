package com.ddnet.dubbo.context.filter;

import lombok.extern.slf4j.Slf4j;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * 验签工具类
 *
 * @author Vinson.Ding
 * @date 2019/3/4
 */
@Slf4j
public class SignUtils {
    private SignUtils() {
        //default construct
    }

    public static final String PROTOCOL_INJVM = "injvm";
    public static final int SIGN_ERROR_CODE = 16;

    public static String sign(String token, String body) {
        return sha256(body, token);
    }

    private static String sha256(String content, String seToken) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(content.getBytes(UTF_8));
            if(seToken!=null) {
                md.update(seToken.getBytes(UTF_8));
            }
            return Base64.getEncoder().encodeToString(md.digest());
        } catch (NoSuchAlgorithmException e) {
            log.error("sha error", e);
        }
        return "";
    }
}

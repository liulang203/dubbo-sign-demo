package com.ddnet.dubbo;

import org.apache.dubbo.common.Constants;
import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.common.utils.StringUtils;
import org.apache.dubbo.rpc.*;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

/**
 * 验证签名信息
 * Created by Vinson.Ding on 2019/2/18.
 */
@Activate(group = {Constants.PROVIDER}, value = "sign")
public class SignFilter implements Filter {
    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        String receiveSign = invocation.getAttachment("signContent");
        System.out.println(receiveSign);
        String content = StringUtils.toArgumentString(invocation.getArguments());
        String sign = sign(content);
        if(!sign.equals(receiveSign)){
            throw new RpcException("sign check error");
        }
        return invoker.invoke(invocation);
    }

    private String sign(String content) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            return Base64.getEncoder().encodeToString(md.digest(content.getBytes(StandardCharsets.UTF_8)));
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }
}

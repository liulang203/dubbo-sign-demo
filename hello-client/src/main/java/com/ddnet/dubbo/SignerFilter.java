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
 * 生成签名信息
 * @author Vinson.Ding
 * @date 2019/2/18
 */
@Activate(group = {Constants.CONSUMER}, value = "signer")
public class SignerFilter implements Filter {
    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        System.out.println("signer call");
        String content = StringUtils.toArgumentString(invocation.getArguments());
        String sign = sign(content);
        RpcContext.getContext().setAttachment("signContent",sign);
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

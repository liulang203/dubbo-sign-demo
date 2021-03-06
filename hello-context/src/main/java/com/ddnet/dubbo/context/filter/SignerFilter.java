package com.ddnet.dubbo.context.filter;

import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.common.Constants;
import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.common.utils.StringUtils;
import org.apache.dubbo.rpc.*;

import static com.ddnet.dubbo.context.filter.SignUtils.PROTOCOL_INJVM;

/**
 * 生成签名信息<br>
 * JVM 内部调用不进行签名验证
 *
 * @author Vinson.Ding
 * @date 2019/2/18
 */
@Activate(group = {Constants.CONSUMER}, value = "signer")
@Slf4j
public class SignerFilter implements Filter {


    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        //JVM 内部调用不进行签名
        if (PROTOCOL_INJVM.equals(invoker.getUrl().getProtocol())) {
            return invoker.invoke(invocation);
        }
        RpcContext context = RpcContext.getContext();
        String token = context.getUrl().getParameter("signtoken");
        String content = StringUtils.toArgumentString(invocation.getArguments());
        String signContent = SignUtils.sign(token, content);

        context.setAttachment("signContent", signContent);
        Result result = invoker.invoke(invocation);
        return result;

    }
}

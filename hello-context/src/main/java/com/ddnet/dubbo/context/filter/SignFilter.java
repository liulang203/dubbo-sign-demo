package com.ddnet.dubbo.context.filter;

import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.common.Constants;
import org.apache.dubbo.common.extension.Activate;
import org.apache.dubbo.common.utils.StringUtils;
import org.apache.dubbo.rpc.*;

import static com.ddnet.dubbo.context.filter.SignUtils.PROTOCOL_INJVM;
import static com.ddnet.dubbo.context.filter.SignUtils.SIGN_ERROR_CODE;

/**
 * 验证签名信息<br>
 * JVM 内部调用不进行签名验证
 *
 * @author Vinson.Ding
 * @date 2019/2/18
 */
@Activate(group = {Constants.PROVIDER}, value = "sign")
@Slf4j
public class SignFilter implements Filter {

    @Override
    public Result invoke(Invoker<?> invoker, Invocation invocation) throws RpcException {
        //JVM 内部调用不进行签名验证
        if (PROTOCOL_INJVM.equals(invoker.getUrl().getProtocol())) {
            return invoker.invoke(invocation);
        }
        RpcContext context = RpcContext.getContext();
        String receiveSign = context.getAttachment("signContent");
        String token = context.getUrl().getParameter("signtoken");
        String methodName = invocation.getMethodName();
        String content = StringUtils.toArgumentString(invocation.getArguments());
        String sign = SignUtils.sign(token, content);
        if (!sign.equals(receiveSign)) {
            log.warn("check sign fail,data[receiveSign={},token={},methodName={},arguments={}"
                    , receiveSign, token, methodName, StringUtils.toArgumentString(invocation.getArguments()));
            throw new RpcException(SIGN_ERROR_CODE, "sign check error");
        }
        Result result = invoker.invoke(invocation);
        return result;
    }

    @Override
    public Result onResponse(Result result, Invoker<?> invoker, Invocation invocation) {
        String infName = invoker.getInterface().getName() + "." + invocation.getMethodName();
        String remoteHost = RpcContext.getContext().getRemoteHost();
        if (result.hasException()) {
            log.info("service error,info[serviceName={},clientHost={}]", infName, remoteHost);
        }
        return result;
    }
}

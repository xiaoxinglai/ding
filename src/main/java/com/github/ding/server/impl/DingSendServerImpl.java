package com.github.ding.server.impl;

import com.github.ding.annotation.Ding;
import com.github.ding.client.base.impl.DingTalkClientImpl;
import com.github.ding.server.DingSendServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;

/**
 * @ClassName DingSendServerImpl
 * @Author laixiaoxing
 * @Date 2020/1/8 下午9:04
 * @Description TODO
 * @Version 1.0
 */
@Service
public class DingSendServerImpl implements DingSendServer {

    @Autowired
    DingTalkClientImpl dingTalkClient;

    @Override
    @Ding(BizType = "demo",tag = "测试注解问题")
    public void testDemo() {
        int a=9/0;
    }

    @Override
    public void testSend(String bizType) {
        dingTalkClient.sendMsg(bizType,"主动消息发送","主动消息测试",new Exception("异常测试信息"));
    }
}

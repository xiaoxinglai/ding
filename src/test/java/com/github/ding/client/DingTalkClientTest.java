package com.github.ding.client;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;

import static org.junit.Assert.*;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath*:spring.xml"})
public class DingTalkClientTest {


    @Autowired
    private DingTalkClient dingTalkClient;

    /**
     * 普通消息通知
     * @throws Exception
     */
    @Test
    public void sendMsg() throws Exception {
        dingTalkClient.sendMsg("test","普通消息通知测试","今天天气真好");
        Thread.sleep(3000l);
    }


    /**
     * 带异常信息的消息通知
     * @throws Exception
     */
    @Test
    public void sendMsg1() throws Exception {
        try{
            throw  new Exception("异常信息");
        }catch (Exception ex){
            dingTalkClient.sendMsg("test","异常消息通知测试","xx发生异常",ex);
        }
        Thread.sleep(300000l);
    }

    /**
     * 发送时候指定要艾特的人
     * @throws Exception
     */
    @Test
    public void sendMsg2() throws Exception {
        dingTalkClient.sendMsg("test","普通消息通知测试","今天天气真好", Arrays.asList("175125xxxx"));
    }

    @Test
    public void sendMsg3() throws Exception {
    }

    @Test
    public void sendMsg4() throws Exception {
    }

    @Test
    public void sendMsg5() throws Exception {
    }

    @Test
    public void sendMsg6() throws Exception {
    }

    @Test
    public void sendMsg7() throws Exception {
    }

    @Test
    public void sendMsg8() throws Exception {
    }

}
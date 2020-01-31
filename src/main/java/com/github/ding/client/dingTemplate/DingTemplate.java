package com.github.ding.client.dingTemplate;

import com.github.ding.client.DingTalkClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @ClassName DoDingTemplate
 * @Author laixiaoxing
 * @Date 2020/1/20 下午4:44
 * @Description 钉钉发送消息 代码级模版
 * @Version 1.0
 */
@Component
public class DingTemplate {

    @Autowired
    DingTalkClient dingTalkClient;

    /**
     * 取默认配置的艾特
     * @param bizType 业务线tag
     * @param tag 问题标题
     * @param action
     * @param <T>
     * @return
     * @throws RuntimeException
     */
    public <T> T execute(String bizType, String tag, DingSend<T> action) throws RuntimeException {
        T result;
        try {
            result = action.execute();
        } catch (Throwable var5) {
            dingTalkClient.sendMsg(bizType, tag, var5);
            throw new RuntimeException("DingTemplate execute threw exception",var5);
        }
        return result;
    }


    /**
     * 指定艾特人
     * @param bizType 业务线tag
     * @param tag 问题标题
     * @param names
     * @param action
     * @param <T>
     * @return
     * @throws RuntimeException
     */
    public <T> T execute(String bizType, String tag,List<String> names,DingSend<T> action) throws RuntimeException {
        T result;
        try {
            result = action.execute();
        } catch (Throwable var5) {
            dingTalkClient.sendMsg(bizType, tag, var5,names);
            throw new RuntimeException("DingTemplate execute threw exception",var5);
        }
        return result;
    }
}

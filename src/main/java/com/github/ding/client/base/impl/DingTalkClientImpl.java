package com.github.ding.client.base.impl;
import com.alibaba.fastjson.JSONObject;
import com.github.ding.client.DingTalkClient;
import com.github.ding.client.base.AbstractDingTalkClient;
import com.github.ding.enums.TemplateTypeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.util.List;

/**
 * @Author laixiaoxing
 * @Description
 * @Date 下午5:26 2020/1/8
 */
@Slf4j
@Component
public class DingTalkClientImpl extends AbstractDingTalkClient implements DingTalkClient {

    /**
     * 发送消息客户端 取默认的业务线配置上的艾特配置 手动发异常
     * @param bizType
     * @param tag
     * @param ex
     */
    @Override
    public void sendMsg(String bizType, String tag, Throwable ex) {
        doSendMsg(bizType, tag, null, ex, null,config.getTypeDing(bizType), TemplateTypeEnum.DEFAULT.getCode());
    }


    /**
     * 发送消息客户端 取默认的业务线配置上的艾特配置 手动发异常给指定的人
     * @param bizType
     * @param tag
     * @param ex
     */
    @Override
    public void sendMsg(String bizType, String tag, Throwable ex,List<String> atNames){
        doSendMsg(bizType, tag, null, ex, atNames,config.getTypeDing(bizType), TemplateTypeEnum.DEFAULT.getCode());
    }


    /**
     * 发送消息客户端
     *
     * @param bizType 对应钉钉群的业务线编码
     * @param tag     问题tag
     * @param content 报警通知内容
     * @param ex      异常栈
     * @param atNames 要艾特的人列表
     */
    @Override
    public void sendMsg(String bizType, String tag, String content, Throwable ex, List<String> atNames) {
        doSendMsg(bizType, tag, content, ex, atNames,config.getTypeDing(bizType), TemplateTypeEnum.DEFAULT.getCode());
    }


    /**
     * 发送消息客户端
     * @param bizType
     * @param tag
     * @param content
     * @param templateTypeEnum
     */
    @Override
    public void sendMsg(String bizType, String tag, String content,String templateTypeEnum) {
        doSendMsg(bizType, tag, content, null, null,config.getTypeDing(bizType), templateTypeEnum);
    }


    /**
     * 发送消息客户端 取默认的业务线配置上的艾特配置
     *
     * @param bizType 对应钉钉群的业务线编码
     * @param tag     问题tag
     * @param content 报警通知内容
     * @param ex      异常栈
     */
    @Override
    public void sendMsg(String bizType, String tag, String content, Throwable ex) {
        doSendMsg(bizType, tag, content, ex, null,config.getTypeDing(bizType),TemplateTypeEnum.DEFAULT.getCode());
    }



    /**
     * 发送消息客户端 取默认的业务线配置上的艾特配置  且无异常 ，只是想给配置的人发content的内容
     *
     * @param bizType 对应钉钉群的业务线编码
     * @param tag     问题tag
     * @param content 报警通知内容
     */
    @Override
    public void sendMsg(String bizType, String tag, String content) {
        doSendMsg(bizType, tag, content, null, null,config.getTypeDing(bizType), TemplateTypeEnum.NOTIFY.getCode());
    }

    /**
     * 发送消息客户端 取默认的业务线配置上的艾特配置  且无异常 ，只是想给配置的人发content的内容
     *
     * @param bizType 对应钉钉群的业务线编码
     * @param content 报警通知内容
     */
    @Override
    public void sendMsg(String bizType, String content) {
        doSendMsg(bizType, null, content, null, null,config.getTypeDing(bizType), TemplateTypeEnum.NOTIFY.getCode());
    }



    /**
     * 发送消息客户端 取默认的业务线配置上的艾特配置  且无异常 ，只是想给指定的人发content的内容
     *
     * @param bizType 对应钉钉群的业务线编码
     * @param tag     问题tag 问题的标题
     * @param content 报警通知内容
     */
    @Override
    public void sendMsg(String bizType, String tag, String content, List<String> atNames) {
        doSendMsg(bizType, tag, content, null, atNames,config.getTypeDing(bizType),TemplateTypeEnum.NOTIFY.getCode());
    }



    @Override
    public void sendMsg(String bizType, String tag, String content, Throwable ex, List<String> names, Object[] args) {
        doSendMsg(bizType, tag,    content+"|参数为:"+  JSONObject.toJSONString(args), ex, names,config.getTypeDing(bizType),TemplateTypeEnum.DEFAULT.getCode());
    }


}

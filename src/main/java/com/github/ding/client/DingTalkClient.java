package com.github.ding.client;



import java.util.List;

/**
 * @Author laixiaoxing
 * @Description 钉钉机器人消息发送客户端
 * @Date 下午6:07 2020/1/31
 */
public interface DingTalkClient {

    /**
     * 发送消息客户端 取默认的业务线配置上的艾特配置 手动发异常
     * @param bizType
     * @param tag
     * @param ex
     */
    void sendMsg(String bizType, String tag, Throwable ex);

    /**
     * 发送消息客户端 取默认的业务线配置上的艾特配置 手动发异常给指定的人
     * @param bizType
     * @param tag
     * @param ex
     */
     void sendMsg(String bizType, String tag, Throwable ex, List<String> atNames);


    /**
     * 发送消息客户端
     *
     * @param bizType 对应钉钉群的业务线编码
     * @param tag     问题tag
     * @param content 报警通知内容
     * @param ex      异常栈
     * @param atNames 要艾特的人列表
     */
     void sendMsg(String bizType, String tag, String content, Throwable ex, List<String> atNames);

    /**
     * 发送消息客户端
     * @param bizType
     * @param tag
     * @param content
     * @param templateTypeEnum
     */
     void sendMsg(String bizType, String tag, String content, String templateTypeEnum);


    /**
     * 发送消息客户端 取默认的业务线配置上的艾特配置
     *
     * @param bizType 对应钉钉群的业务线编码
     * @param tag     问题tag
     * @param content 报警通知内容
     * @param ex      异常栈
     */
    void sendMsg(String bizType, String tag, String content, Throwable ex);



    /**
     * 发送消息客户端 取默认的业务线配置上的艾特配置  且无异常 ，只是想给配置的人发content的内容
     *
     * @param bizType 对应钉钉群的业务线编码
     * @param tag     问题tag
     * @param content 报警通知内容
     */
     void sendMsg(String bizType, String tag, String content) ;

    /**
     * 发送消息客户端 取默认的业务线配置上的艾特配置  且无异常 ，只是想给配置的人发content的内容
     *
     * @param bizType 对应钉钉群的业务线编码
     * @param content 报警通知内容
     */
     void sendMsg(String bizType, String content);



    /**
     * 发送消息客户端 取默认的业务线配置上的艾特配置  且无异常 ，只是想给指定的人发content的内容
     *
     * @param bizType 对应钉钉群的业务线编码
     * @param tag     问题tag 问题的标题
     * @param content 报警通知内容
     */
     void sendMsg(String bizType, String tag, String content, List<String> atNames) ;


    /**
     * 发送消息客户端  带有方法参数
     * @param bizType
     * @param tag
     * @param content
     * @param ex
     * @param names
     * @param args
     */
     void sendMsg(String bizType, String tag, String content, Throwable ex, List<String> names, Object[] args);
}

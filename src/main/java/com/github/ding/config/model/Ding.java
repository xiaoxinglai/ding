package com.github.ding.config.model;

import com.google.common.util.concurrent.RateLimiter;
import lombok.Data;

import java.util.List;

/**
 * @ClassName Ding
 * @Author laixiaoxing
 * @Date 2020/1/8 上午11:40
 * @Description  钉钉业务线通知
 * @Version 1.0
 */
@Data
public class Ding {
    /**
     * 业务线
     */
    private String bizType;
    /**
     * 发送的钉钉群url
     */
    private String url;

    /**
     * 要艾特的人的手机号列表
     */
    private List<String> name;

    /**
     * 加密签名 不填就不加密
     */
    private String secret;

    /**
     * 日志异常栈启用配置 0-启用本地内存 1-启用三方存储
     */
    private String localException;

    /**
     * 三方日志采集平台配置的项目名称
     */
    private String logName;

    /**
     * 属于这个钉钉机器人的限流器 官方是限制每分钟不能超过20条发送消息
     */
    RateLimiter rateLimiter = RateLimiter.create(20.0 / 60.0 *0.9);


}

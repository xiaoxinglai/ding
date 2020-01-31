package com.github.ding.filter;


import com.github.ding.config.model.WarningMessage;
import com.github.ding.config.model.Ding;


/**
 * @ClassName DingFilter
 * @Author laixiaoxing
 * @Date 2020/1/12 下午3:27
 * @Description 钉钉消息过滤器
 * @Version 1.0
 */
public interface DingFilter {

    /**
     * 过滤处理器
     *
     * @param warningMessage
     * @param ex
     * @param ding
     * @throws Exception
     */
    boolean handle(WarningMessage warningMessage, Throwable ex, Ding ding) throws Exception;


    /**
     * 返回过滤器的优先级
     * @return
     */
    int orderBy();
}

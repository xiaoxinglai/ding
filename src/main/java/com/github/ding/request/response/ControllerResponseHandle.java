package com.github.ding.request.response;

import com.github.ding.server.model.Param;

/**
 * @Author laixiaoxing
 * @Description  接收http请求之后的返回处理
 * @Date 下午6:08 2020/1/12
 */
public interface ControllerResponseHandle {

    /**
     * 处理的请求类型
     *
     * @return
     */
    String getRequestType();


    /**
     *  处理
     * @param param 请求参数
     */
    String handle(Param param) throws Exception;
}

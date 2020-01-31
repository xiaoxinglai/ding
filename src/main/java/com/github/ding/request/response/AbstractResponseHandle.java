package com.github.ding.request.response;

import com.github.ding.client.base.impl.DingTalkClientImpl;
import com.github.ding.server.model.Param;
import com.github.ding.config.model.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

/**
 * @ClassName AbstractResponseHandle
 * @Author laixiaoxing
 * @Date 2020/1/12 下午5:37
 * @Description 抽象请求处理器
 * @Version 1.0
 */
public abstract class AbstractResponseHandle<T> implements ControllerResponseHandle {

    protected final String Line = "\r\n";

    @Autowired
    DingTalkClientImpl dingTalkClient;

    @Override
    public String handle(Param param) throws Exception {
        Result result = doHandle(param);
        String content = getDingContent(result);
        if (!StringUtils.isEmpty(content)) {
            dingTalkClient.sendMsg(param.getBizType(), param.getTag(), content,getTemplate());
        }
       return getResponse(result);
    }


    /**
     * 采用的消息模版类型
     * @return
     */
    protected abstract String getTemplate();

    /**
     * 处理请求
     * @param param
     * @return
     */
    protected abstract Result<T> doHandle(Param param);


    /**
     * 返回的网页内容
     * @param param
     * @return
     */
    protected abstract String getResponse(Result<T> param);

    /**
     * 操作之后返回的钉钉的内容反馈
     * @param param
     * @return
     */
    protected abstract String getDingContent(Result<T> param);

}

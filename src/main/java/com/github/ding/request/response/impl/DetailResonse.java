package com.github.ding.request.response.impl;

import com.github.ding.config.Config;
import com.github.ding.config.model.Result;
import com.github.ding.enums.RequestTypeEnum;
import com.github.ding.enums.TemplateTypeEnum;
import com.github.ding.request.response.AbstractResponseHandle;
import com.github.ding.server.model.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @ClassName FileResonse
 * @Author laixiaoxing
 * @Date 2020/1/12 下午6:15
 * @Description 归档响应
 * @Version 1.0
 */
@Service
public class DetailResonse extends AbstractResponseHandle<String> {

    @Autowired
    Config config;

    @Override
    protected String getTemplate() {
        return TemplateTypeEnum.NOTIFY_NO_URL.getCode();
    }

    @Override
    protected Result<String> doHandle(Param param) {
        Throwable exception = config.getExceptionCache(param.getTag());
        if (Objects.isNull(exception)) {
            return new Result<>("异常栈已经被清空");
        } else {
            StringBuilder sb = new StringBuilder();
            for (StackTraceElement stackTraceElement : exception.getStackTrace()) {
                sb.append(stackTraceElement + "</br>");
            }
            return new Result<>(sb.toString());
        }
    }

    @Override
    protected String getResponse(Result<String> param) {
       return param.getData();

    }

    @Override
    protected String getDingContent(Result<String> param) {
        return "该问题tag的异常栈被查看";
    }

    @Override
    public String getRequestType() {
        return RequestTypeEnum.DETAI.getCode();
    }


}

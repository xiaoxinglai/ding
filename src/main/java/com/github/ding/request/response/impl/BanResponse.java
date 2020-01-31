package com.github.ding.request.response.impl;

import com.github.ding.enums.RequestTypeEnum;
import com.github.ding.config.model.Result;
import com.github.ding.enums.StatusEnum;
import com.github.ding.enums.TemplateTypeEnum;
import com.github.ding.request.response.AbstractResponseHandle;
import com.github.ding.server.BanServer;
import com.github.ding.server.model.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName BanResponse
 * @Author laixiaoxing
 * @Date 2020/1/12 下午6:06
 * @Description 静默响应
 * @Version 1.0
 */
@Service
public class BanResponse extends AbstractResponseHandle<String> {

    @Autowired
    BanServer banServer;

    @Override
    protected String getTemplate() {
        return TemplateTypeEnum.NOTIFY_NO_URL.getCode();
    }

    @Override
    protected Result<String> doHandle(Param param) {
        banServer.modifyBan(param);
        return new Result<>(param.getStatus());
    }

    @Override
    protected String getResponse(Result<String> param) {
        return param.getData().equals(StatusEnum.OPEN.getCode()) ? "取消静默成功" : "静默成功";
    }

    @Override
    protected String getDingContent(Result<String> param) {
        return param.getData().equals(StatusEnum.OPEN.getCode()) ? "取消静默成功" : "静默成功";
    }

    @Override
    public String getRequestType() {
        return RequestTypeEnum.BAN.getCode();
    }

}

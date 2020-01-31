package com.github.ding.request.response.impl;

import com.github.ding.enums.RequestTypeEnum;
import com.github.ding.enums.StatusEnum;
import com.github.ding.request.response.AbstractResponseHandle;
import com.github.ding.server.FileServer;
import com.github.ding.config.model.Result;
import com.github.ding.enums.TemplateTypeEnum;
import com.github.ding.server.model.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName FileResonse
 * @Author laixiaoxing
 * @Date 2020/1/12 下午6:15
 * @Description 归档响应
 * @Version 1.0
 */
@Service
public class FileResonse extends AbstractResponseHandle<String> {

    @Autowired
    FileServer fileServer;

    @Override
    protected String getTemplate() {
        return TemplateTypeEnum.NOTIFY_NO_URL.getCode();
    }

    @Override
    protected Result<String> doHandle(Param param) {
        fileServer.modifyFile(param);
        return new Result<>(param.getStatus());
    }

    @Override
    protected String getResponse(Result param) {
        return  param.getData().equals(StatusEnum.OPEN.getCode())?"取消归档成功":"归档成功";
    }

    @Override
    protected String getDingContent(Result param) {
        return  param.getData().equals(StatusEnum.OPEN.getCode())?"取消归档成功":"归档成功";
    }

    @Override
    public String getRequestType() {
        return RequestTypeEnum.FILE.getCode();
    }

}

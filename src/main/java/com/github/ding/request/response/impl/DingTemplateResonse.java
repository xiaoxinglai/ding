package com.github.ding.request.response.impl;

import com.github.ding.enums.RequestTypeEnum;
import com.github.ding.client.dingTemplate.DingTemplate;
import com.github.ding.config.model.Result;
import com.github.ding.enums.TemplateTypeEnum;
import com.github.ding.request.response.AbstractResponseHandle;
import com.github.ding.server.model.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName FileResonse
 * @Author laixiaoxing
 * @Date 2020/1/12 下午6:15
 * @Description 测试响应
 * @Version 1.0
 */
@Service
public class DingTemplateResonse extends AbstractResponseHandle<String> {

    @Autowired
    DingTemplate dingTemplate;

    @Override
    protected String getTemplate() {
        return TemplateTypeEnum.NOTIFY_NO_URL.getCode();
    }

    @Override
    protected Result doHandle(Param param) {
        dingTemplate.execute("test", "dingTemplate代码测试", () -> {
            int a = 1 / 0;
            return a;
        });
        return null;
    }

    @Override
    protected String getResponse(Result<String> param) {
        return "测试响应";
    }

    @Override
    protected String getDingContent(Result<String> param) {
        return "";
    }

    @Override
    public String getRequestType() {
        return RequestTypeEnum.DINGTEMPLATE.getCode();
    }

}

package com.github.ding.template.impl;

import com.github.ding.config.Config;
import com.github.ding.config.model.ContentModel;
import com.github.ding.config.model.Ding;
import com.github.ding.enums.TemplateTypeEnum;
import com.github.ding.template.AbstractTemplateConvert;
import com.github.ding.template.MarkdownTemplate;
import org.springframework.stereotype.Component;

/**
 * @ClassName DefaultTemplate
 * @Author laixiaoxing
 * @Date 2020/1/13 上午12:23
 * @Description 默认模版
 * @Version 1.0
 */
@Component
public class NotifyNoUrlTemplate extends AbstractTemplateConvert {

    @Override
    protected String getBodyText(StringBuilder sb, ContentModel contentModel, Config config, Ding ding,
            MarkdownTemplate markdownTemplate) throws Exception {
        sb.append("#### 通知:\n > " + contentModel.getNotify() + "\n");
        return sb.toString();

    }


    @Override
    public String modeType() {
        return TemplateTypeEnum.NOTIFY_NO_URL.getCode();
    }
}

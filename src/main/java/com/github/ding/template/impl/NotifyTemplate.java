package com.github.ding.template.impl;

import com.github.ding.config.Config;
import com.github.ding.config.model.ContentModel;
import com.github.ding.config.utils.UrlUtils;
import com.github.ding.enums.RequestTypeEnum;
import com.github.ding.enums.StatusEnum;
import com.github.ding.template.AbstractTemplateConvert;
import com.github.ding.template.MarkdownTemplate;
import com.github.ding.config.model.Ding;
import com.github.ding.enums.TemplateTypeEnum;
import org.springframework.stereotype.Component;

/**
 * @ClassName DefaultTemplate
 * @Author laixiaoxing
 * @Date 2020/1/13 上午12:23
 * @Description 默认模版
 * @Version 1.0
 */
@Component
public class NotifyTemplate extends AbstractTemplateConvert {

    @Override
    protected String getBodyText(StringBuilder sb, ContentModel contentModel, Config config, Ding ding,
            MarkdownTemplate markdownTemplate) throws Exception {
        sb.append("#### 通知:\n > " + contentModel.getNotify() + "\n");
        sb.append(" ######  [静默](" + UrlUtils
                .getDetailURl(config.getUrl(), contentModel.getTag(), RequestTypeEnum.BAN.getCode(),
                        StatusEnum.CLOSE.getCode(), ding.getBizType()));

        sb.append(")|[取消静默](" + UrlUtils
                .getDetailURl(config.getUrl(), contentModel.getTag(), RequestTypeEnum.BAN.getCode(),
                        StatusEnum.OPEN.getCode(), ding.getBizType()) + ")   \n");
        return sb.toString();

    }


    @Override
    public String modeType() {
        return TemplateTypeEnum.NOTIFY.getCode();
    }
}

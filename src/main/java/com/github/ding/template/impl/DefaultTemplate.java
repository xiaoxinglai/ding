package com.github.ding.template.impl;

import com.github.ding.config.Config;
import com.github.ding.config.utils.UrlUtils;
import com.github.ding.enums.RequestTypeEnum;
import com.github.ding.enums.StatusEnum;
import com.github.ding.template.AbstractTemplateConvert;
import com.github.ding.template.MarkdownTemplate;
import com.github.ding.config.model.ContentModel;
import com.github.ding.config.model.Ding;
import com.github.ding.enums.TemplateTypeEnum;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * @ClassName DefaultTemplate
 * @Author laixiaoxing
 * @Date 2020/1/13 上午12:23
 * @Description 默认模版
 * @Version 1.0
 */
@Component
public class DefaultTemplate extends AbstractTemplateConvert {

    @Override
    protected String getBodyText(StringBuilder sb, ContentModel contentModel, Config config, Ding ding,
            MarkdownTemplate markdownTemplate) throws Exception {
        sb.append("> 类名 " + contentModel.getClassName() + "\n\n");
        sb.append("> 方法名" + contentModel.getMethodName() + "\n\n");
        sb.append("> 行号" + contentModel.getLines() + "\n\n");
        sb.append("#### 通知内容:\n > " + contentModel.getNotify() + "\n");
        if (!StringUtils.isEmpty(contentModel.getException())) {
            sb.append("[查询异常栈详情](" + contentModel.getException() + ")\n");
        }
        sb.append(" ######  [静默](" + UrlUtils
                .getDetailURl(config.getUrl(), contentModel.getTag(), RequestTypeEnum.BAN.getCode(),
                        StatusEnum.CLOSE.getCode(), ding.getBizType()));

        sb.append(")|[取消静默](" + UrlUtils
                .getDetailURl(config.getUrl(), contentModel.getTag(), RequestTypeEnum.BAN.getCode(),
                        StatusEnum.OPEN.getCode(), ding.getBizType()) + ")   \n");

        // sb.append(" ######  [归档](http://www.thinkpage.cn/)|[取消归档](http://www.thinkpage.cn/)   \n");
        return sb.toString();

    }


    @Override
    public String modeType() {
        return TemplateTypeEnum.DEFAULT.getCode();
    }
}

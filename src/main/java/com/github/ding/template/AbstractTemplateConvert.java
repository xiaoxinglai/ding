package com.github.ding.template;

import com.github.ding.config.Config;
import com.github.ding.config.model.ContentModel;
import com.github.ding.config.model.Ding;
import com.github.ding.config.model.WarningMessage;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @ClassName AbstractTemplateConvert
 * @Author laixiaoxing
 * @Date 2020/1/13 上午12:13
 * @Description 抽象模版生成类
 * @Version 1.0
 */
public abstract class AbstractTemplateConvert implements TemplateConvert {


    @Override
    public MarkdownTemplate templateConvert(WarningMessage warningMessage, Ding ding, Config config) throws Exception {
        MarkdownTemplate markdownTemplate = new MarkdownTemplate();
        if (CollectionUtils.isEmpty(warningMessage.getAtNames())) {
            markdownTemplate.setAtNames(ding.getName());
        } else {
            markdownTemplate.setAtNames(warningMessage.getAtNames());
        }
        ContentModel contentModel = warningMessage.getContentModel();
        markdownTemplate.setTitle(getTitle(contentModel));
        markdownTemplate.setBizType(ding.getBizType());
        markdownTemplate.setTag(contentModel.getTag());
        markdownTemplate.setCause(contentModel.getNotify());
        markdownTemplate.setText(getText(contentModel, config, ding, markdownTemplate));
        return markdownTemplate;
    }

    /**
     * 获取模版内容
     *
     * @param contentModel
     * @param config
     * @param ding
     * @param markdownTemplate
     * @return
     * @throws Exception
     */
    private String getText(ContentModel contentModel, Config config, Ding ding, MarkdownTemplate markdownTemplate)
            throws Exception {
        StringBuilder sb = new StringBuilder();
        sb.append("#### 问题标签:" + contentModel.getTag() + "\n\n" + getAtNamesContent(markdownTemplate.getAtNames())
                + "\n\n");
        getBodyText(sb, contentModel, config, ding, markdownTemplate);
        return sb.toString();
    }


    /**
     * 获取模版中内容体
     *
     * @param sb
     * @param contentModel
     * @param config
     * @param ding
     * @param markdownTemplate
     * @return
     * @throws Exception
     */
    protected abstract String getBodyText(StringBuilder sb, ContentModel contentModel, Config config, Ding ding,
            MarkdownTemplate markdownTemplate) throws Exception;


    private String getAtNamesContent(List<String> atNames) {
        StringBuilder sb = new StringBuilder();
        if (!CollectionUtils.isEmpty(atNames)) {
            for (String atName : atNames) {
                sb.append("@" + atName);
            }
        }
        return sb.toString();
    }


    private String getTitle(ContentModel contentModel) {
        return "问题标签:" + contentModel.getTag();
    }

}

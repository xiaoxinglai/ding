package com.github.ding.template;

import com.github.ding.config.Config;
import com.github.ding.config.model.WarningMessage;
import com.github.ding.config.model.Ding;

public interface TemplateConvert {

    /**
     * 模版转换
     * @param warningMessage
     * @param ding
     * @param config
     * @return
     * @throws Exception
     */
    MarkdownTemplate templateConvert(WarningMessage warningMessage, Ding ding, Config config) throws Exception;

    /**
     * 模版类型
     * @return
     */
    String modeType();
}

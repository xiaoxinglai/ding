package com.github.ding.config.model;

import lombok.Data;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * @ClassName WarningMessage
 * @Author laixiaoxing
 * @Date 2020/1/10 上午1:28
 * @Description 报警信息
 * @Version 1.0
 */

@Data
public class WarningMessage {

    /**
     * 内容
     */
    private ContentModel contentModel;

    /**
     * at的人
     */
    private List<String> atNames;


    /**
     * 消息模版类型
     */
    private String modeType;

    /**
     * 构造message
     *
     * @param tag
     * @param content
     * @param ex
     * @param atNames
     */
    public WarningMessage(String tag, String content, Throwable ex, List<String> atNames,String modeType) {
        this.modeType=modeType;
        this.contentModel = new ContentModel();
        this.atNames = atNames;

        String name;
        String method;
        String lines;

        if (Objects.isNull(ex)) {
            this.contentModel.setNotify(content);
            StackTraceElement[] stackTraceElements = Thread.currentThread().getStackTrace();
            name = stackTraceElements[4].getClassName();
            method = stackTraceElements[4].getMethodName();
            lines = stackTraceElements[4].getLineNumber() + "";
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append(ex + "\n");
            StackTraceElement[] stackTraceElements = ex.getStackTrace();
            sb.append(stackTraceElements[0].toString() + "\n");
            this.contentModel.setNotify(Optional.ofNullable(content).orElse("") + "\n" + sb.toString());
            name = stackTraceElements[0].getClassName();
            method = stackTraceElements[0].getMethodName();
            lines = stackTraceElements[0].getLineNumber() + "";
        }


        if (StringUtils.isEmpty(tag)) {
            this.contentModel.setTag(name + "." + method + ":" + lines);
        } else {
            this.contentModel.setTag(tag);
        }
        this.contentModel.setClassName(name);
        this.contentModel.setMethodName(method);
        this.contentModel.setLines(lines);
    }


}

package com.github.ding.config.model;
import lombok.Data;



/**
 * @ClassName ContentModel
 * @Author laixiaoxing
 * @Date 2020/1/10 上午1:33
 * @Description 内容模版
 * @Version 1.0
 */
@Data
public class ContentModel {

    /**
     * 通知tag
     */
    private String tag;

    /**
     * 报错类
     */
    private String className;


    /**
     * 报错方法
     */
    private String methodName;

    /**
     *报错行
     */
    private String lines;

    /**
     * 需要的通知文本
     */
    private String notify;

    /**
     * 异常栈详情(url标签)
     */
    private String  exception;

}

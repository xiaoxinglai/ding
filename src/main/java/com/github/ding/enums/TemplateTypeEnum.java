package com.github.ding.enums;

/**
 * @ClassName StatusEnum
 * @Author laixiaoxing
 * @Date 2020/1/12 下午6:28
 * @Description 状态枚举
 * @Version 1.0
 */
public enum TemplateTypeEnum {


    /**
     * 默认通知模版
     */
    DEFAULT("默认通知模版", "default"),

    /**
     *静默通知模版
     */
    NOTIFY("通知模版", "notify"),

    /**
     *静默通知模版
     */
    NOTIFY_NO_URL("通知模版无url", "notify_no_url"),


    ;


    private String msg;
    private String code;

    TemplateTypeEnum(String msg, String code) {
        this.msg = msg;
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public String getCode() {
        return code;
    }


}

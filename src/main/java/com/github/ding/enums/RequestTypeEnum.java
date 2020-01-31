package com.github.ding.enums;

public enum RequestTypeEnum {


    BAN("静默类型", "ban"), FILE("归档类型", "file"), TEST("测试类型", "test"), DETAI("异常明细类型", "detail"),
    DINGTEMPLATE("代码模版测试","dingTemplate");

    private String msg;
    private String code;

    RequestTypeEnum(String msg, String code) {
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

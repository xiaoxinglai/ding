package com.github.ding.enums;

/**
 * @ClassName StatusEnum
 * @Author laixiaoxing
 * @Date 2020/1/12 下午6:28
 * @Description 状态枚举
 * @Version 1.0
 */
public enum StatusEnum {


    /**
     * 生效
     */
    OPEN("开启", "1"),

    /**
     * 关闭
     */
    CLOSE("关闭", "0"),;

    private String msg;
    private String code;

    StatusEnum(String msg, String code) {
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

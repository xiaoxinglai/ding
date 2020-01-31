package com.github.ding.server.model;

import lombok.Data;

import java.net.URLDecoder;

/**
 * @ClassName Param
 * @Author laixiaoxing
 * @Date 2020/1/8 下午3:29
 * @Description 参数
 * @Version 1.0
 */
@Data
public class Param {
    String type;
    String tag;
    String status;
    String bizType;

    public Param(String msg) throws Exception {
        String[] arrays = msg.split(" ");
        String[] values = arrays[1].split("&");
        type = values[0].split("=")[1];
        tag = URLDecoder.decode(values[1].split("=")[1], "utf8");
        status = values[2].split("=")[1];
        bizType = values[3].split("=")[1];
    }

    public Param(String type, String tag, String status, String bizType) {
        this.type = type;
        this.tag = tag;
        this.status = status;
        this.bizType = bizType;
    }
}

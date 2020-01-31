package com.github.ding.config.model;

import lombok.Data;

/**
 * @ClassName Result
 * @Author laixiaoxing
 * @Date 2020/1/12 下午6:45
 * @Description 处理的返回结果
 * @Version 1.0
 */
@Data
public class Result<T> {

    private T data;

    private Integer code;

    private String msg;

    public Result(T data) {
        this.data = data;
    }
}

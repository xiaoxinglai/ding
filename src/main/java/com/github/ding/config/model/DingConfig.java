package com.github.ding.config.model;

import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @ClassName DingConfig
 * @Author laixiaoxing
 * @Date 2020/1/8 上午11:39
 * @Description 钉钉通知配置
 * @Version 1.0
 */
@Component
public class DingConfig {
    /**
     * 域名
     */
    private String url;


    /**
     * 业务线配置列表
     */
    private List<Ding> ding;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<Ding> getList() {
        return ding;
    }

    public void setList(List<Ding> list) {
        this.ding = list;
    }

    @Override
    public String toString() {
        return "DingConfig{" + "url='" + url + '\'' + ", ding=" + ding + '}';
    }

}

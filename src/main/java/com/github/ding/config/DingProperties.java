package com.github.ding.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

/**
 * @ClassName HelloProperties
 * @Author laixiaoxing
 * @Date 2020/5/10 上午12:08
 * @Description TODO
 * @Version 1.0
 */

@ConfigurationProperties(prefix = "ding")
public class DingProperties {

    private String postUrl;

    private Map<String,DingConfig> bizList;


    public Map<String, DingConfig> getBizList() {
        return bizList;
    }

    public void setBizList(Map<String, DingConfig> bizList) {
        this.bizList = bizList;
    }

    public String getPostUrl() {
        return postUrl;
    }

    public void setPostUrl(String postUrl) {
        this.postUrl = postUrl;
    }
}

package com.github.ding.config;

import java.util.List;

/**
 * @ClassName DingConfig
 * @Author laixiaoxing
 * @Date 2020/5/10 上午11:48
 * @Description 钉钉业务配置
 * @Version 1.0
 */
public class DingConfig {
   private String  url;
    private String secret;
    private List<String> name;
   private String localException;
   private String logName;

    public String getLogName() {
        return logName;
    }

    public void setLogName(String logName) {
        this.logName = logName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public List<String> getName() {
        return name;
    }

    public void setName(List<String> name) {
        this.name = name;
    }

    public String getLocalException() {
        return localException;
    }

    public void setLocalException(String localException) {
        this.localException = localException;
    }
}

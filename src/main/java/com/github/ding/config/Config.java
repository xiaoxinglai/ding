package com.github.ding.config;

import com.github.ding.config.cache.ExceptionCache;
import com.github.ding.config.model.Ding;
import com.github.ding.config.model.DingConfig;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

//import com.souche.ding.request.httpserver.HttpServer;

/**
 * @ClassName Config
 * @Author laixiaoxing
 * @Date 2020/1/8 上午11:25
 * @Description 启动配置
 * @Version 1.0
 */
public class Config {


    private ExecutorService sendThreadExecutor;

    private DingConfig dingConfig;

    private Map<String, Ding> bizTypeConfig;

    private ExceptionCache exceptionCache = new ExceptionCache();

    public Throwable getExceptionCache(String batchNo) {
        return exceptionCache.get(batchNo);
    }

    public void setExceptionCache(String batchNo, Throwable ex) {
        this.exceptionCache.put(batchNo, ex);
    }


    public void doInit() throws Exception {
        //初始化业务线列表
        initBizType();
        //初始化线程池大小 每个机器人一个线程
        initThreadPool();
        //启动http响应服务 以便接受静默或者归档请求
        // httpServer.start(dingConfig.getPort());
    }

    private void initThreadPool() {
        sendThreadExecutor = new ThreadPoolExecutor(dingConfig.getList().size(), dingConfig.getList().size(),
                60L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<Runnable>(2000));
    }



    public Ding getTypeDing(String biz) {
        return bizTypeConfig.get(biz);
    }

    /**
     * 通过配置初始化业务线列表
     */
    private void initBizType() {
        if (!CollectionUtils.isEmpty(dingConfig.getList())) {
            HashMap hashMap = new HashMap(16);
            for (Ding ding : dingConfig.getList()) {
                hashMap.put(ding.getBizType(), ding);
            }
            bizTypeConfig = hashMap;
        }
    }


    /**
     * 读取配置 进行初始化
     */
    public void initConfigByProperties(DingProperties dingProperties) {
        String url =dingProperties.getPostUrl();
        List<Ding> dings = new ArrayList();
        for (Map.Entry<String, com.github.ding.config.DingConfig> kv : dingProperties.getBizList()
                .entrySet()) {
            Ding ding = new Ding();
            ding.setBizType(kv.getKey());
            com.github.ding.config.DingConfig type =kv.getValue();
            ding.setUrl(type.getUrl());
            ding.setName(type.getName());
            ding.setSecret(type.getSecret());
            ding.setLocalException(type.getLocalException());
            ding.setLogName(type.getLogName());
            dings.add(ding);

        }

        DingConfig dingConfig = new DingConfig();
        dingConfig.setUrl(url);
        dingConfig.setList(dings);
        this.dingConfig = dingConfig;
    }


    public String getUrl() {
        return dingConfig.getUrl();
    }

    public ExecutorService getSendThreadExecutor() {
        return sendThreadExecutor;
    }
}

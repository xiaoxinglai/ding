package com.github.ding.config;

import com.github.ding.config.cache.ExceptionCache;
import com.github.ding.config.model.Ding;
import com.github.ding.config.model.DingConfig;
import com.github.ding.config.utils.ConfigurationUtils;
import com.github.ding.server.BanServer;
import com.github.ding.server.FileServer;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @ClassName Config
 * @Author laixiaoxing
 * @Date 2020/1/8 上午11:25
 * @Description 启动配置
 * @Version 1.0
 */
@Component
public class Config implements InitializingBean {

    @Autowired
    BanServer banServer;
    @Autowired
    FileServer fileServer;

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

    @Override
    public void afterPropertiesSet() throws Exception {
        //初始化配置
        initConfig();
        //初始化业务线列表
        initBizType();
        //初始化静默列表
        initBan();
        //初始化归档列表
        initFile();
        //初始化线程池大小 每个机器人一个线程
        initThreadPool();
        //启动http响应服务 以便接受静默或者归档请求
        // httpServer.start(dingConfig.getPort());
    }

    private void initThreadPool() {
        sendThreadExecutor = Executors.newFixedThreadPool(dingConfig.getList().size());
    }


    private void initFile() {
        //todo 新增拓展接口，以便于可以拓展成从其他地方获取归档列表
        fileServer.initFile();
    }


    /**
     * 初始化禁默列表
     */
    private void initBan() {
        //todo 新增拓展接口，以便于可以拓展成从其他地方获取禁默列表
        banServer.initBan();
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
    private void initConfig() {
        new ConfigurationUtils("/ding-config.properties");
        String url = ConfigurationUtils.getPropertiesByKey("ding.post.url");
        Map<String, String> tags = ConfigurationUtils.getPropertiesByPre("ding.send");
        HashMap<String, Map<String, String>> bizType = new HashMap<String, Map<String, String>>(16);
        for (Map.Entry<String, String> keyValue : tags.entrySet()) {
            String[] keys = keyValue.getKey().split("\\.");
            String biz = keys[keys.length - 1];
            if (biz.equals("url")) {
                continue;
            }
            String type = keys[keys.length - 2];
            if (bizType.get(biz) == null) {
                Map<String, String> bizValue = new HashMap<String, String>();
                bizValue.put(type, keyValue.getValue());
                bizType.put(biz, bizValue);
            } else {
                bizType.get(biz).put(type, keyValue.getValue());
            }
        }

        List<Ding> dings = new ArrayList();
        for (Map.Entry<String, Map<String, String>> stringMapEntry : bizType.entrySet()) {
            Ding ding = new Ding();
            ding.setBizType(stringMapEntry.getKey());
            Map<String, String> type = stringMapEntry.getValue();
            ding.setUrl(type.get("url"));
            ding.setName(Arrays.asList(type.get("name").split(",")));
            ding.setSecret(type.get("secret"));
            ding.setLocalException(type.get("localException"));
            ding.setLogName(type.get("logName"));
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

package com.github.ding.config;

import com.github.ding.server.BanServer;
import com.github.ding.server.FileServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @ClassName DingAutoConfiguration
 * @Author laixiaoxing
 * @Date 2020/5/10 上午11:03
 * @Description 钉钉自动配置
 * @Version 1.0
 */
@Configuration
@ConditionalOnWebApplication
@EnableConfigurationProperties(DingProperties.class)
public class DingAutoConfiguration {

    @Autowired
    public DingProperties dingProperties;
    @Autowired
    BanServer banServer;
    @Autowired
    FileServer fileServer;


    @Bean
    Config createConfig() throws Exception {
        Config config=new Config();
        config.initConfigByProperties(dingProperties);
        config.doInit();
        //初始化静默列表
        initBan();
        //初始化归档列表
        initFile();
        return config;
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


}

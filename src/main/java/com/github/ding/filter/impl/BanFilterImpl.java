package com.github.ding.filter.impl;

import com.github.ding.config.model.WarningMessage;
import com.github.ding.config.model.ContentModel;
import com.github.ding.config.model.Ding;
import com.github.ding.filter.DingFilter;
import com.github.ding.server.BanServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


/**
 * @ClassName LogServerImpl
 * @Author laixiaoxing
 * @Date 2020/1/10 下午4:55
 * @Description 记录日志和url
 * @Version 1.0
 */
@Slf4j
@Component
public class BanFilterImpl implements DingFilter {

    @Autowired
    private BanServer banServer;

    @Override
    public boolean handle(WarningMessage warningMessage, Throwable ex, Ding ding) throws Exception {
        ContentModel contentModel = warningMessage.getContentModel();
        return banServer.isBan(contentModel.getTag());
    }


    @Override
    public int orderBy() {
        return 200;
    }
}

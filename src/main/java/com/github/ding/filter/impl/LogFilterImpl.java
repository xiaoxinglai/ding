package com.github.ding.filter.impl;

import com.github.ding.config.Config;
import com.github.ding.config.model.WarningMessage;
import com.github.ding.config.model.ContentModel;
import com.github.ding.config.model.Ding;
import com.github.ding.config.utils.UrlUtils;
import com.github.ding.enums.StatusEnum;
import com.github.ding.filter.DingFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;


/**
 * @ClassName LogServerImpl
 * @Author laixiaoxing
 * @Date 2020/1/10 下午4:55
 * @Description 记录日志和url
 * @Version 1.0
 */
@Slf4j
@Component
public class LogFilterImpl implements DingFilter {

    @Autowired
    public Config config;

    @Override
    public boolean handle(WarningMessage warningMessage, Throwable ex, Ding ding) throws Exception {
        if (Objects.isNull(ex)) {
            return true;
        }
        ContentModel contentModel = warningMessage.getContentModel();
        String batchNo = contentModel.getTag() +"-"+System.currentTimeMillis();
        log.error("钉钉报警异常批次号{}", batchNo, ex);
        String url;
        if (StatusEnum.CLOSE.getCode().equals(ding.getLocalException())) {
            //本地缓存
            config.setExceptionCache(batchNo, ex);
            url = UrlUtils.getDetailURl(config.getUrl(), batchNo, "detail", StatusEnum.OPEN.getCode(),ding.getBizType());
        } else {
            //三方缓存
            url = "http://logplatform.xxxxx-inc.com/#/apps?appName=" + ding.getLogName() + "&tid=" + batchNo;
        }
        contentModel.setException(url);
        return true;
    }


    @Override
    public int orderBy() {
        return 300;
    }
}

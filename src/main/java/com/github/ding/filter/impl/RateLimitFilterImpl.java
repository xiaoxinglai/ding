package com.github.ding.filter.impl;
import com.github.ding.config.model.WarningMessage;
import com.github.ding.config.model.Ding;
import com.github.ding.filter.DingFilter;
import lombok.extern.slf4j.Slf4j;
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
public class RateLimitFilterImpl implements DingFilter {


    @Override
    public boolean handle(WarningMessage warningMessage, Throwable ex, Ding ding) throws Exception {
        ding.getRateLimiter().acquire();
        return true;
    }


    @Override
    public int orderBy() {
        return 500;
    }


}

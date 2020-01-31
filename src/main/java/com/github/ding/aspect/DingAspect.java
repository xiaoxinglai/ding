package com.github.ding.aspect;

import com.github.ding.annotation.Ding;
import com.github.ding.client.base.impl.DingTalkClientImpl;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;

/**
 * @ClassName RoleAspect
 * @Author laixiaoxing
 * @Date 2019/3/24 下午10:54
 * @Description 校验方法的权限
 * @Version 1.0
 */
@Slf4j
@Aspect
@Component
public class DingAspect {

    @Autowired
    DingTalkClientImpl dingTalkClient;

    @Around("@annotation(com.github.ding.annotation.Ding)")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        Object[] args = joinPoint.getArgs();
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        Ding ding = method.getAnnotation(Ding.class);

        try {
            Object res = joinPoint.proceed();
            return res;
        } catch (Exception ex) {
            String bizType = StringUtils.isEmpty(ding.BizType()) ? null : ding.BizType();
            String tag = StringUtils.isEmpty(ding.tag()) ? null : ding.tag();
            String content = StringUtils.isEmpty(ding.content()) ? "" : ding.content();
            List<String> names = StringUtils.isEmpty(ding.name()[0]) ? null : Arrays.asList(ding.name());
            dingTalkClient.sendMsg(bizType, tag,content, ex, names,args);
            throw ex;
        }
    }



}

package com.github.ding.config.cache;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @ClassName ExceptionCache
 * @Author laixiaoxing
 * @Date 2020/1/10 下午5:06
 * @Description 大小为50的LRU缓存  可以做成配置
 * @Version 1.0
 */
public class ExceptionCache extends LinkedHashMap<String,Throwable>{

    private int size= 50;

    @Override
    protected boolean removeEldestEntry(Map.Entry<String, Throwable> eldest) {
        return super.size()>size;
    }
}

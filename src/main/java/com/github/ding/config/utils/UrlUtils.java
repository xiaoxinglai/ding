package com.github.ding.config.utils;

import java.net.UnknownHostException;

/**
 * @ClassName UrlUtils
 * @Author laixiaoxing
 * @Date 2020/1/10 下午5:55
 * @Description TODO
 * @Version 1.0
 */
public class UrlUtils {


    public static String getDetailURl(String prex,String tag,String type,String status,String bizType) throws UnknownHostException {
        String url=prex.trim()+"/ding/?type="+type+"&tag="+tag+"&status="+status+"&bizType="+bizType;
        return url;
    }
}

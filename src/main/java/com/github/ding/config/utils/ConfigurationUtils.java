package com.github.ding.config.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * @ClassName ConfigurationUtils
 * @Author laixiaoxing
 * @Date 2019/6/2 上午11:43
 * @Description 获取配置信息的工具类
 * @Version 1.0
 */
public class ConfigurationUtils {

    public static Properties properties;

    public ConfigurationUtils(String propertiesPath) {
        properties = this.getBeanScanPath(propertiesPath);
    }


    /**
     * 读取配置文件
     *
     * @param propertiesPath
     * @return
     */
    private Properties getBeanScanPath(String propertiesPath) {

        if (propertiesPath == "" || propertiesPath == null) {
            propertiesPath = "/ding-config.properties";
        }

        Properties properties = new Properties();
        //通过类的加载器获取具有给定名称的资源
        InputStream in = ConfigurationUtils.class.getResourceAsStream(propertiesPath);
        try {
            System.out.println("正在加载配置文件"+propertiesPath);
            properties.load(in);
            System.out.println("加载配置完成"+propertiesPath);
            return properties;
        } catch (IOException e) {
            e.fillInStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return properties;
    }


    /**
     * 根据配置文件的key获取value的值
     *
     * @param propertiesKey
     * @return
     */
    public static String getPropertiesByKey(String propertiesKey) {
        if (properties.size() > 0) {
            return (String)properties.get(propertiesKey);
        }
        return null;
    }


    /**
     * 通过前缀返回分组
     * @param pre
     * @return
     */
    public static Map<String,String> getPropertiesByPre(String pre) {
        if (properties.size() > 0) {
            Map<String,String> res=new HashMap<String, String>();
            for (Map.Entry<Object, Object> keyValue : properties.entrySet()) {
             String key=(String)keyValue.getKey();
                if (key.startsWith(pre)){
                    res.put(key,(String)keyValue.getValue());
                }
            }
            return res;
        }
        return null;
    }

}

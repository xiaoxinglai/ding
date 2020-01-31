package com.github.ding.server.impl;

import com.github.ding.enums.StatusEnum;
import com.github.ding.server.FileServer;
import com.github.ding.server.model.Param;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @ClassName BanServerImpl
 * @Author laixiaoxing
 * @Date 2020/1/8 下午3:43
 * @Description 归档服务
 * @Version 1.0
 */
@Service
public class FileServerImpl implements FileServer {

    private Map<String, Long> fileList = new HashMap<String, Long>();


    ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);

    @Override
    public Map initFile() {
        initThreadPool();
        return fileList;
    }

    @Override
    public Map modifyFile(Param param) {
        if (param.getStatus().equals(StatusEnum.OPEN.getCode())) {
            //解除归档
            fileList.remove(param.getTag());
            //todo 线程池中的任务也要移除掉 如果已经存在有部分归档的内容 也发送掉
        } else {
            //归档默认为30分钟归档
            fileList.put(param.getTag(), 30 * 60L);
            addFileWork(param.getTag(), 30 * 60L);
        }
        return fileList;
    }


    public void addFileWork(String tag, Long time) {
        scheduledExecutorService.scheduleAtFixedRate(() -> {
            System.out.println("发送归档任务" + tag);
        }, 0,time, TimeUnit.SECONDS);
    }


    /**
     * 归档线程池
     */
    private void initThreadPool() {
        if (!CollectionUtils.isEmpty(fileList)) {
            for (Map.Entry<String, Long> file : fileList.entrySet()) {
                scheduledExecutorService.schedule(() -> {
                    System.out.println("发送归档任务" + file.getKey());
                }, file.getValue(), TimeUnit.SECONDS);
            }
        }
    }


}

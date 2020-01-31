package com.github.ding.server;

import com.github.ding.server.model.Param;

import java.util.Map;

public interface BanServer {

    /**
     * 初始化
     * @return
     */
    Map initBan();

    /**
     * 更新列表
     * @param param
     * @return
     */
    Map modifyBan(Param param);


    /**
     * 判断是否静默
     */
    boolean isBan(String tag);

}

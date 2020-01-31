package com.github.ding.server;

import com.github.ding.server.model.Param;

import java.util.Map;

public interface FileServer {

    /**
     * 初始化
     * @return
     */
    Map initFile();

    /**
     * 更新列表
     * @param param
     * @return
     */
    Map modifyFile(Param param);



}

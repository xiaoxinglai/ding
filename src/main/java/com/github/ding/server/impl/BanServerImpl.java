package com.github.ding.server.impl;

import com.github.ding.enums.StatusEnum;
import com.github.ding.server.BanServer;
import com.github.ding.server.model.Param;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName BanServerImpl
 * @Author laixiaoxing
 * @Date 2020/1/8 下午3:43
 * @Description 获取静默列表
 * @Version 1.0
 */
@Service
public class BanServerImpl implements BanServer {

    private HashMap<String, Boolean> banList = new HashMap();

    @Override
    public Map initBan() {
        return banList;
    }

    @Override
    public Map modifyBan(Param param) {
        if (StatusEnum.OPEN.getCode().equals(param.getStatus())) {
            //解除静默
            banList.remove(param.getTag());
        } else {
            banList.put(param.getTag(), true);
        }

        return banList;
    }

    @Override
    public boolean isBan(String tag) {
        return banList.get(tag)==null?true:false;
    }
}

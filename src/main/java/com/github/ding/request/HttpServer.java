package com.github.ding.request;

import com.github.ding.config.Config;
import com.github.ding.request.response.ControllerResponseHandle;
import com.github.ding.server.model.Param;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;


/**
 * @ClassName HttpServer
 * @Author laixiaoxing
 * @Date 2020/1/14 下午8:55
 * @Description
 * @Version 1.0
 */
@Controller
public class HttpServer implements ApplicationContextAware {


    @Autowired
    Config config;

    HashMap<String, ControllerResponseHandle> hashMap;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        hashMap = new HashMap<>();
        for (Map.Entry<String, ControllerResponseHandle> kv : applicationContext
                .getBeansOfType(ControllerResponseHandle.class).entrySet()) {
            hashMap.put(kv.getValue().getRequestType(), kv.getValue());
        }
    }

    /**
     * 钉钉访问服务
     *
     * @return
     */
    @RequestMapping(value = "/ding", method = RequestMethod.GET,produces = "text/plain;charset=UTF-8")
    @ResponseBody
    public String dingRequest(@RequestParam(value = "type") String type, @RequestParam(value = "tag",required = false) String tag,
            @RequestParam(value = "status",required = false) String status, @RequestParam(value = "bizType",required = false) String bizType)
            throws Exception {

        Param param = new Param(type, tag, status, bizType);
        try {
            String res = hashMap.get(param.getType()).handle(param);
            return res;
        } catch (Exception ex) {
            throw ex;
        }
    }
}

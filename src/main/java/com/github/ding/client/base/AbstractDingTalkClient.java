package com.github.ding.client.base;

import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.request.OapiRobotSendRequest;
import com.dingtalk.api.response.OapiRobotSendResponse;
import com.github.ding.config.Config;
import com.github.ding.config.model.Ding;
import com.github.ding.config.model.WarningMessage;
import com.github.ding.filter.DingFilter;
import com.github.ding.template.MarkdownTemplate;
import com.github.ding.template.TemplateConvert;
import com.taobao.api.ApiException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.util.CollectionUtils;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

/**
 * @Author laixiaoxing
 * @Description
 * @Date 下午5:26 2020/1/8
 */
@Slf4j
public abstract class AbstractDingTalkClient implements ApplicationContextAware {
    @Autowired
    protected Config config;

    List<DingFilter> dingFilters;

    HashMap<String, TemplateConvert> templateConvertMap = new HashMap<>();


    private OapiRobotSendRequest request = new OapiRobotSendRequest();
    private OapiRobotSendRequest.Markdown markdown = new OapiRobotSendRequest.Markdown();


    /**
     * 获取spring的容器
     *
     * @param applicationContext
     * @throws BeansException
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        initFilters(applicationContext);
        initTemplateConvert(applicationContext);

    }

    /**
     * 初始化模版转换器
     *
     * @param applicationContext
     */
    private void initTemplateConvert(ApplicationContext applicationContext) {
        applicationContext.getBeansOfType(TemplateConvert.class).forEach((k, v) -> {
            templateConvertMap.put(v.modeType(), v);
        });
    }

    /**
     * 初始化过滤器
     *
     * @param applicationContext
     */
    private void initFilters(ApplicationContext applicationContext) {
        dingFilters = new ArrayList<>();
        applicationContext.getBeansOfType(DingFilter.class).forEach((k, v) -> {
            dingFilters.add(v);
        });
        //按orderBy从小到大排序
        dingFilters.stream().sorted((o1, o2) -> {
            if (o1.orderBy() < o2.orderBy()) {
                return 1;
            } else { return -1;}
        });
    }

    /**
     * post请求方法
     *
     * @param url              发送的加签url
     * @param markdownTemplate 消息模版
     */
    protected void post(final String url, MarkdownTemplate markdownTemplate) {
        DefaultDingTalkClient client = new DefaultDingTalkClient(url);
        request.setMsgtype("markdown");
        markdown.setTitle(markdownTemplate.getTitle());
        markdown.setText(markdownTemplate.getText());
        request.setMarkdown(markdown);
        if (!CollectionUtils.isEmpty(markdownTemplate.getAtNames())){
            OapiRobotSendRequest.At at = new OapiRobotSendRequest.At();
            at.setAtMobiles(markdownTemplate.getAtNames());
            request.setAt(at);
        }
        try {
            OapiRobotSendResponse response = client.execute(request);
            if (response.isSuccess()) {
                log.info("业务线{}的问题{}钉钉发送成功", markdownTemplate.getBizType(), markdownTemplate.getTag());
            } else {
                log.error("业务线{}的问题{}，原因{},钉钉发送失败", markdownTemplate.getBizType(), markdownTemplate.getTag(),
                        markdownTemplate.getCause());
            }
        } catch (ApiException e) {
            log.error("业务线{}的问题{}，原因{},钉钉发送异常", markdownTemplate.getBizType(), markdownTemplate.getTag(),
                    markdownTemplate, e);
        }
    }


    /**
     * 获取加签url
     *
     * @param ding
     * @return
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     * @throws UnsupportedEncodingException
     */
    protected String getUrl(Ding ding)
            throws NoSuchAlgorithmException, InvalidKeyException, UnsupportedEncodingException {
        //如果没有秘钥就不进行加密
        if (org.springframework.util.StringUtils.isEmpty(ding.getSecret())) {
            return ding.getUrl();
        }
        Long timestamp = System.currentTimeMillis();
        String stringToSign = timestamp + "\n" + ding.getSecret();
        Mac mac = Mac.getInstance("HmacSHA256");
        mac.init(new SecretKeySpec(ding.getSecret().getBytes("UTF-8"), "HmacSHA256"));
        byte[] signData = mac.doFinal(stringToSign.getBytes("UTF-8"));
        String sign = URLEncoder.encode(new String(Base64.encodeBase64(signData)), "UTF-8");
        return ding.getUrl() + "&timestamp=" + timestamp + "&sign=" + sign;
    }


    /**
     * 发送消息
     *
     * @param warningMessage
     * @param ding
     * @throws Exception
     */
    protected void sendMsg(WarningMessage warningMessage, Ding ding) throws Exception {
        String url = getUrl(ding);
        MarkdownTemplate markdownTemplate = templateConvertMap.get(warningMessage.getModeType())
                .templateConvert(warningMessage, ding, config);
        post(url, markdownTemplate);
    }


    /**
     * 处理消息发送
     *
     * @param bizType
     * @param tag
     * @param content
     * @param ex
     * @param atNames
     * @param ding
     */
    protected void doSendMsg(String bizType, String tag, String content, Throwable ex, List<String> atNames, Ding ding,
            String templateType) {
        config.getSendThreadExecutor().execute((() -> {
            WarningMessage warningMessage = new WarningMessage(tag, content, ex, atNames, templateType);
            if (Objects.isNull(ding)) {
                log.error("该bizType配置{},无法找到对应的钉钉群配置", bizType);
                return;
            }
            try {
                for (DingFilter handle : dingFilters) {
                    try {
                        if (!handle.handle(warningMessage, ex, ding)) {
                            return;
                        }
                    } catch (Exception e) {
                        log.error("发送钉钉消息，过滤器链路异常", e);
                        return;
                    }
                }
                sendMsg(warningMessage, ding);
            } catch (Exception e) {
                log.error("发送钉钉消息参数异常", e);
            }
        }));
    }
}

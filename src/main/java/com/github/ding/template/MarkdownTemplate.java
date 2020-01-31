package com.github.ding.template;

import lombok.Data;
import java.util.List;

/**
 * @ClassName MarkdownTemplate
 * @Author laixiaoxing
 * @Date 2020/1/10 上午1:50
 * @Description markdown模版1
 * @Version 1.0
 */
@Data
public class MarkdownTemplate {

    private String title;

    private String text;

    private List<String> atNames;

    private String bizType;

    private String tag;

    private String cause;

}

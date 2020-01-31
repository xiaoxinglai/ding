package com.github.ding.annotation;

import java.lang.annotation.*;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.METHOD})
public @interface Ding {

    /**
     * bizType 业务线
     *
     * @return
     */
    String BizType() default "";
    /**
     * tag 问题唯一标签
     *
     * @return
     */
    String tag() default "";

    /**
     * content 内容
     */
    String content() default "";

    /**
     * 艾特的人
     * @return
     */
    String[] name() default "";
}

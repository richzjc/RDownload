package com.richzjc.rdownload.notification.anotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 正在下载的条目 进度发生了改变需要用到这个注解
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ProgressSubscribe {
    String configurationKey() default "";
}

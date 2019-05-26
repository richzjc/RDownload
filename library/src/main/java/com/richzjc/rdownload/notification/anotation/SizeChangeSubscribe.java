package com.richzjc.rdownload.notification.anotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 下载队列的长度改变了需要用到这个注解
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface SizeChangeSubscribe {
    String configurationKey() default "";
}

package com.wangzhen.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Description:
 *
 * @author wangzhen
 * @version 1.0
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.CLASS)
public @interface Student {
    float height();

    int age();
}

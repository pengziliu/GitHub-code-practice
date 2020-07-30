package com.lzn.mybatisplus.codegenerator.utils.export;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 导出的字段注解
 */
@Target({ElementType.FIELD,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ExportField {

    /**
     * 导出的列名
     */
    String name() default "";
    /**
     * 导出的列宽 只在导出方式是Excel时有效
     */
    int length() default 120;
    /**
     * 是否导出
     */
    boolean ignore() default false;
    /**
     *  排序
     */
    int order() default 99;

}

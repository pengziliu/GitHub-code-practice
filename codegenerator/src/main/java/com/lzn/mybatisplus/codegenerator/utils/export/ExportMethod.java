package com.lzn.mybatisplus.codegenerator.utils.export;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 导出的注解类
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ExportMethod {
    /**
     * 指定一个导出的查询类
     */
    Class<? extends ExportService> serviceClass();

    /**
     * 本导出操作的备注
     */
    String memo() default "";

}

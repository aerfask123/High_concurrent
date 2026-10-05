package com.xxjob.demo.annotation;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Cached {

    String key() default "";

    String value() default "";

    int expire() default 0;

    String title() default "";

    String logService() default "operLogServiceImpl";

    boolean isSaveRequestData() default true;

    boolean isTrack() default true;
}

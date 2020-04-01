package com.ssanusi.javaoauth2.logging;

import org.springframework.boot.logging.LogLevel;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;

@Component
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
public @interface Loggable {

    LogLevel value() default LogLevel.DEBUG;

    boolean params() default true;

    boolean result() default true;
}

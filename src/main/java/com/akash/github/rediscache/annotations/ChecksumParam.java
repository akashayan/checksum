package com.akash.github.rediscache.annotations;

import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface ChecksumParam {

    int order();

    String name() default "";
}

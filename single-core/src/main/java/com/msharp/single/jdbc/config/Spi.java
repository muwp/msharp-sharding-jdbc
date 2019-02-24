package com.msharp.single.jdbc.config;

import java.lang.annotation.*;

/**
 * Spi
 *
 * @author mwup
 * @version 1.0
 * @created 2019/02/22 13:51
 **/
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface Spi {

    String name() default "";

    Scope scope() default Scope.SINGLETON;
}

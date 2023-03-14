package org.ashe.alpha.auth.domain.annotation;

import java.lang.annotation.*;

/**
 * 权限控制注解，作用于某些特定权限才能执行的功能
 */
@Target(ElementType.METHOD) // 作用于方法
@Retention(RetentionPolicy.RUNTIME)
public @interface Auth {

}

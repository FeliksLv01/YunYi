package com.kcqnly.application.common;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;

/**
 * 树父id
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(FIELD)
public @interface TreeFid {
}
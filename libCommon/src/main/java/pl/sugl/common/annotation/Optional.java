package pl.sugl.common.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * author: Hanson
 * date:   2018/3/1
 * describe:
 */
@Documented
@Target(ElementType.PARAMETER)
@Retention(RUNTIME)
public @interface Optional {
}

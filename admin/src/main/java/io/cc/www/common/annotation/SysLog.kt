package io.cc.www.common.annotation

/**
 * 系统日志注解
 *
 * @author Mark sunlightcs@gmail.com
 * @since 1.3.0 2017-03-08
 */
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER)
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
annotation class SysLog(val value: String = "")

package com.wangzhen.baselib.http.mock

/**
 * Description: Mock 的注解类
 *
 * @author wangzhen
 * @version 1.0
 */
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class Mock(val value: String = "", val assets: String = "", val url: String = "")

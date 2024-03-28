package com.cding.common.exception

import android.util.Log
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlin.coroutines.CoroutineContext

/**
 * @author cding
 * @date 2024/1/4
 * 全局协程异常处理器
 */
class GlobalCoroutineExceptionHandler : CoroutineExceptionHandler{

    override val key: CoroutineContext.Key<*> = CoroutineExceptionHandler

    override fun handleException(context: CoroutineContext, exception: Throwable) {
        Log.d("Exception", "=_=Global Exception: ${exception.printStackTrace()}")
    }
}
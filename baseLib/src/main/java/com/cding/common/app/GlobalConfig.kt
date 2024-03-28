package com.cding.common.app


import com.cding.common.network.ExceptionHandle
import com.blankj.utilcode.util.ToastUtils
import kotlinx.coroutines.CoroutineExceptionHandler


/**
 *  @author cding
 *  @date 2023/11/12
 *
 */
object GlobalConfig {
    private val defNetworkException = CoroutineExceptionHandler { _, throwable ->
        val exception = ExceptionHandle.handleException(throwable)
        ToastUtils.showShort(exception.errMsg)
    }

    private var mNetworkException: CoroutineExceptionHandler? = null


    //所有协程启动的时候需要设置异常处理器为该全局的异常处理器。
    val networkException: CoroutineExceptionHandler
        get() = this.mNetworkException ?: defNetworkException


    fun setNetworkException(networkException: CoroutineExceptionHandler) = apply {
        this.mNetworkException = networkException
    }

}
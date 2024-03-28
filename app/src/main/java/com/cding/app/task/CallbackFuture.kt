package com.cding.app.task

import com.blankj.utilcode.util.LogUtils
import java.util.concurrent.CountDownLatch
import java.util.concurrent.ExecutionException
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException


/**
 * @author cding
 * @date 2023/4/6
 */
class CallbackFuture<T> : Future<T> {
    private val readyLatch = CountDownLatch(1)
    private var result: T? = null
    private var exception: Exception? = null

    private fun checkNotSet() {
        if (readyLatch.count == 0L) {
            throw Exception("")
//            Timber.e("Result has already been set!")
        }
    }

    override fun set(e: Exception) {
        checkNotSet()
        exception = e
        readyLatch.countDown()
    }

    override fun set(v: T) {
        checkNotSet()
        result = v
        readyLatch.countDown()
    }

    override fun cancel(mayInterruptIfRunning: Boolean): Boolean {
        throw UnsupportedOperationException()
    }

    override fun isCancelled(): Boolean {
        // impl the cancel
        return false
    }

    override fun isDone(): Boolean {
        return readyLatch.count == 0L
    }

    override fun get(): T? {
        readyLatch.await()
        if (null != exception) {
            throw ExecutionException(exception)
        }
        return result
    }

    override fun get(timeout: Long, unit: TimeUnit): T? {
        if (!readyLatch.await(timeout, unit)) {
            LogUtils.d("callbackFuture Timeout超时")
            throw TimeoutException("callbackFuture Timeout超时")
        }
        if (null != exception) {
            throw ExecutionException(exception)
        }
        return result
    }

}
package com.cding.app.ui.study.task

import java.util.concurrent.ExecutionException
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

/**
 * @author cding
 * @date 2022/02/04
 */
interface Future<V> {

    fun cancel(mayInterruptIfRunning: Boolean): Boolean

    fun isCancelled(): Boolean

    fun isDone(): Boolean

    @Throws(InterruptedException::class,
        ExecutionException::class
    )
    fun get(): V?

    @Throws(
        InterruptedException::class,
        ExecutionException::class,
        TimeoutException::class
    )
    fun get(timeout: Long, unit: TimeUnit): V?

    fun set(v: V)

    fun set(e: Exception)

}
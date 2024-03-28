package com.cding.app.network.entity

import com.cding.common.base.IBaseResponse


/**
 * @author cding
 * @date 2019/11/01
 */
data class Response<T>(
    val errorMsg: String,
    val errorCode: Int,
    val data: T
) : IBaseResponse<T> {

    override fun code() = errorCode

    override fun msg() = errorMsg

    override fun data() = data

    override fun isSuccess() = errorCode == 0

}
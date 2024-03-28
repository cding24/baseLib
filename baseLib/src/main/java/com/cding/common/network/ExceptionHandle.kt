package com.cding.common.network

import android.net.ParseException
import com.google.gson.JsonParseException
import com.google.gson.stream.MalformedJsonException
import org.json.JSONException
import retrofit2.HttpException
import java.net.ConnectException

/**
 *   @author : cding
 *   @date   : 2019/08/12
 *   全局处理异常
 */
object ExceptionHandle {

    fun handleException(excep: Throwable): ResponseThrowable {
        val ex: ResponseThrowable
        when (excep) {
            is ResponseThrowable -> {
                ex = excep
            }
            is HttpException -> {
                ex = ResponseThrowable(ERROR.HTTP_ERROR, excep)
            }
            is JsonParseException, is JSONException, is ParseException, is MalformedJsonException -> {
                ex = ResponseThrowable(ERROR.PARSE_ERROR, excep)
            }
            is ConnectException -> {
                ex = ResponseThrowable(ERROR.NETWORK_ERROR, excep)
            }
            is javax.net.ssl.SSLException -> {
                ex = ResponseThrowable(ERROR.SSL_ERROR, excep)
            }
            is java.net.SocketTimeoutException -> {
                ex = ResponseThrowable(ERROR.TIMEOUT_ERROR, excep)
            }
            is java.net.UnknownHostException -> {
                ex = ResponseThrowable(ERROR.TIMEOUT_ERROR, excep)
            }
            else -> {
                ex = if (!excep.message.isNullOrEmpty()){
                    ResponseThrowable(1000, excep.message!!, excep)
                } else {
                    ResponseThrowable(ERROR.UNKNOWN, excep)
                }
            }
        }

        return ex
    }


}
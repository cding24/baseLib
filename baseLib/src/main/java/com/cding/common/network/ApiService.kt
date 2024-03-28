package com.cding.common.network

import com.cding.common.entity.User
import com.cding.common.entity.UserInfo
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query


interface ApiService {

    @POST("action/login")
    suspend fun login(@Body user: User): Response<UserInfo>

    @GET("backend-service/config")
    fun getServerCfg(@Query("env") env: String): Response<String?>

}
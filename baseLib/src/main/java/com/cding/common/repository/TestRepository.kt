package com.cding.common.repository

import com.cding.common.entity.User
import com.cding.common.network.ApiService
import com.cding.common.network.HttpClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TestRepository {
    private val service: ApiService by lazy {
        HttpClient.getInstance().getService(ApiService::class.java, "https://www.wanandroid.com/")
    }

    suspend fun login(user: User) = withContext(Dispatchers.IO) {
        service.login(user = user)
    }

}
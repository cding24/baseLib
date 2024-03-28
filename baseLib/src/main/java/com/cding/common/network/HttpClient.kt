package com.cding.common.network

import com.aleyn.cache.CacheManager
import com.aleyn.cache.NetCacheInterceptor
import com.blankj.utilcode.util.Utils
import com.cding.common.adapter.FlowAdapterFactory
import okhttp3.ConnectionPool
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class HttpClient private constructor() {
    private val serviceMap = hashMapOf<String, Any>()
    var isRxAndroid = false

    companion object{
        private lateinit var httpClient: HttpClient

        fun getInstance(): HttpClient {
            if(!this::httpClient.isInitialized || httpClient == null){
                synchronized(HttpClient::class.java){
                    if(!this::httpClient.isInitialized || httpClient == null){
                        httpClient = HttpClient()
                    }
                }
            }
            return httpClient
        }
    }

    private val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(10L, TimeUnit.SECONDS)
        .readTimeout(10L, TimeUnit.SECONDS)
        .writeTimeout(10L, TimeUnit.SECONDS)
        .addInterceptor{ chain ->
            val request = chain.request().newBuilder().apply {
//                addHeader("Authorization", "token")
            }.build()
            chain.proceed(request = request)
        }
        .connectionPool(ConnectionPool(8, 15, TimeUnit.SECONDS))
        .addInterceptor(NetCacheInterceptor(CacheManager(Utils.getApp().cacheDir)))
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        .build()

//    private val retrofit = Retrofit.Builder()
//        .addCallAdapterFactory(FlowAdapterFactory.create(true))
//        .addConverterFactory(GsonConverterFactory.create())
//        .client(okHttpClient)
//        .baseUrl("https://www.wanandroid.com/")
//        .build()
//
//    val apiService = retrofit.create(ApiService::class.java)

    fun <T : Any> getService(serviceClass: Class<T>, baseUrl: String? = null): T {
        val className = serviceClass.name
        val service = serviceMap[className]

        return if (service == null) {
            var builder = Retrofit.Builder()
                .client(okHttpClient)
                .addCallAdapterFactory(FlowAdapterFactory.create(true))
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(baseUrl)
            if (isRxAndroid) {
                builder = builder.addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            }
            val newService = builder.build().create(serviceClass)
            serviceMap[className] = newService
            newService as T
        } else {
            service as T
        }
    }

}
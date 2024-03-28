package com.cding.app.network.api

import com.aleyn.cache.CacheStrategy
import com.cding.app.network.entity.Response
import com.cding.app.network.entity.BannerBean
import com.cding.app.network.entity.HomeListBean
import com.cding.app.network.entity.NavTypeBean
import com.cding.app.network.entity.UsedWeb
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

/**
 *   @author : cidng
 *   @date   : 2019/10/29
 */
interface HomeServiceApi {

    /**
     * 玩安卓轮播图
     */
    @GET("banner/json")
    fun getBanner(
        @Header(CacheStrategy.CACHE_MODE) cacheModel: String
    ): Flow<Response<List<BannerBean>>>


    /**
     * 导航数据
     */
    @GET("project/tree/json")
    suspend fun naviJson(): Response<List<NavTypeBean>>


    /**
     * 项目列表
     * @param page 页码，从0开始
     */
    @GET("article/listproject/{page}/json")
    fun getHomeList(
        @Path("page") page: Int,
        @Header(CacheStrategy.CACHE_MODE) cacheModel: String
    ): Flow<Response<HomeListBean>>


    /**
     * 项目列表
     * @param page 页码，从0开始
     */
    @GET("project/list/{page}/json")
    suspend fun getProjectList(
        @Path("page") page: Int,
        @Query("cid") cid: Int
    ): Response<HomeListBean>


    /**
     * 常用网站
     */
    @GET("friend/json")
    suspend fun getPopularWeb(): Response<MutableList<UsedWeb>>
}
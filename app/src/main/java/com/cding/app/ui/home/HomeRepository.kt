package com.cding.app.ui.home

import com.aleyn.cache.CacheMode
import com.cding.common.base.BaseRepository
import com.cding.app.network.entity.Response
import com.cding.app.network.api.HomeServiceApi
import com.cding.app.network.entity.BannerBean
import com.cding.app.network.entity.HomeListBean
import com.cding.common.network.Constants
import com.cding.common.network.HttpClient
import kotlinx.coroutines.flow.Flow

/**
 *   @author : cidng
 *   time   : 2019/10/29
 */
class HomeRepository private constructor() : BaseRepository() {
    private val mService: HomeServiceApi by lazy {
        HttpClient.getInstance().getService(HomeServiceApi::class.java, Constants.BASE_URL)
    }

    companion object {
        @Volatile
        private var INSTANCE: HomeRepository? = null

        fun getInstance() = INSTANCE ?: synchronized(this) {
            INSTANCE ?: HomeRepository().also { INSTANCE = it }
        }
    }

    fun getBannerData(refresh: Boolean): Flow<Response<List<BannerBean>>> {
        val cacheModel = if (refresh){
            CacheMode.NETWORK_PUT_CACHE
        } else {
            CacheMode.READ_CACHE_NETWORK_PUT
        }
        return mService.getBanner(cacheModel)
    }

    fun getHomeList(page: Int, refresh: Boolean = false): Flow<Response<HomeListBean>> {
        val cacheModel = if (refresh){
            CacheMode.NETWORK_PUT_CACHE
        } else {
            CacheMode.READ_CACHE_NETWORK_PUT
        }
        return mService.getHomeList(page, cacheModel)
    }

    suspend fun getNaviJson() = mService.naviJson()

    suspend fun getProjectList(page: Int, cid: Int) = mService.getProjectList(page, cid)

    suspend fun getPopularWeb() = mService.getPopularWeb()


}
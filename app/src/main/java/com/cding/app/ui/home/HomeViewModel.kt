package com.cding.app.ui.home

import androidx.lifecycle.LiveData
import com.cding.common.base.BaseViewModel
import com.cding.common.event.SingleLiveEvent
import com.cding.common.ext.asResponse
import com.cding.common.ext.bindLoading
import com.cding.app.network.entity.BannerBean
import com.cding.app.network.entity.HomeListBean
import com.cding.app.utils.InjectorUtil
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.onCompletion

/**
 *
 * @author cding
 * @date 2020/11/11
 */
class HomeViewModel : BaseViewModel() {
    private val homeRepository by lazy { InjectorUtil.getHomeRepository() }

    private val _banners = MutableSharedFlow<List<BannerBean>>()
    val mBanners: SharedFlow<List<BannerBean>> = _banners

    private val _projectData = MutableSharedFlow<HomeListBean>()
    val projectData: SharedFlow<HomeListBean> = _projectData

    private val _refreshState = SingleLiveEvent<Boolean>()
    val refreshState: LiveData<Boolean> = _refreshState


    /**
     * Banner
     */
    fun getBanner(refresh: Boolean = false) {
        launch {
            homeRepository.getBannerData(refresh)
                .asResponse()
                .collect(_banners)
        }
    }

    /**
     * @param page 页码
     * @param refresh 是否刷新
     */
    fun getHomeList(page: Int, refresh: Boolean = false) {
        launch {
            homeRepository.getHomeList(page, refresh)
                .asResponse()
                .onCompletion { if (refresh) _refreshState.call() }
                .bindLoading(this@HomeViewModel)
                .collect(_projectData)
        }
    }

}
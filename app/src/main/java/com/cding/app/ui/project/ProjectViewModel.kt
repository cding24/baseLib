package com.cding.app.ui.project

import androidx.databinding.ObservableArrayList
import com.cding.app.R
import com.cding.common.base.BaseViewModel
import com.cding.common.event.Message
import com.cding.common.ext.getOrThrow
import com.google.android.material.tabs.TabLayout
import com.cding.app.network.entity.ArticlesBean
import com.cding.app.network.entity.NavTypeBean
import com.cding.app.utils.InjectorUtil
import me.tatarka.bindingcollectionadapter2.ItemBinding
import com.cding.common.BR


/**
 *   @author : cding
 *   @date   : 2019/11/12
 */
class ProjectViewModel : BaseViewModel() {

    private val homeRepository by lazy { InjectorUtil.getHomeRepository() }
    private val itemOnClickListener = object : OnItemClickListener {
        override fun onItemClick(item: ArticlesBean) {
            defUI.msgEvent.postValue(Message(0, obj = item))
        }
    }
    var navTitle = ObservableArrayList<String>()
    var navData = ObservableArrayList<NavTypeBean>()
    var items = ObservableArrayList<ArticlesBean>()
    var itemBinding = ItemBinding.of<ArticlesBean>(BR.itemBean, R.layout.item_project_list)


    private var page: Int = 0

    /**
     * 顺序请求
     */
    fun getFirstData() {
        launch {
            //tab 数据
            val navResult = homeRepository.getNaviJson().getOrThrow()
            navData.addAll(navResult)
            navResult.forEach { item -> navTitle.add(item.name) }

            //tab对应列表数据
            val listBean = homeRepository.getProjectList(page, navResult.first().id).getOrThrow()
            items.addAll(listBean.datas)
        }
    }

    fun getProjectList(cid: Int) {
        launch {
            homeRepository.getProjectList(page, cid).getOrThrow().let {
                items.clear()
                items.addAll(it.datas)
            }
        }
    }


    var tabOnClickListener = object : TabLayout.OnTabSelectedListener {
        override fun onTabReselected(p0: TabLayout.Tab?) {
        }

        override fun onTabUnselected(p0: TabLayout.Tab?) {
        }

        override fun onTabSelected(p0: TabLayout.Tab?) {
            p0?.let {
                getProjectList(navData[it.position].id)
            }
        }
    }


    interface OnItemClickListener {
        fun onItemClick(item: ArticlesBean)
    }
}
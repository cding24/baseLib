package com.cding.app.ui.home

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.cding.app.R
import com.cding.common.base.BaseVMFragment
import com.cding.app.databinding.HomeFragmentBinding
import com.cding.app.network.entity.ArticlesBean
import com.cding.app.network.entity.BannerBean
import com.cding.app.ui.detail.DetailActivity
import com.cding.app.utils.CBannerAdapter
import com.youth.banner.Banner


/**
 *   使用ViewBinding
 *   @author : cding
 *   @date  : 2019/11/02
 */
class HomeFragment : BaseVMFragment<HomeViewModel, HomeFragmentBinding>() {
    companion object {
        fun newInstance() = HomeFragment()
    }

    private lateinit var banner: Banner<BannerBean, CBannerAdapter>
    private val mAdapter by lazy { HomeListAdapter() }
    private var page: Int = 0


    override fun initView(savedInstanceState: Bundle?) {
        mBinding.refreshLay.setOnRefreshListener {
            dropDownRefresh()
        }
        //banner
        banner = Banner(context)
        banner.minimumWidth = MATCH_PARENT
        banner.layoutParams = ViewGroup.LayoutParams(MATCH_PARENT, resources.getDimension(R.dimen.dp_120).toInt())
        banner.setAdapter(CBannerAdapter())

        mBinding.homeRV.layoutManager = LinearLayoutManager(context)
        mBinding.homeRV.adapter = mAdapter
        mAdapter.apply {
            addHeaderView(banner)
            loadMoreModule.setOnLoadMoreListener(this@HomeFragment::loadMore)
            setOnItemClickListener { adapter, _, position ->
                val item = adapter.data[position] as ArticlesBean
                val intent = Intent(context, DetailActivity::class.java)
                intent.putExtra("url", item.link)
                startActivity(intent)
            }
        }
    }

    override fun initObserve() {
        viewModel.refreshState.observe(this@HomeFragment) {
            if (mBinding.refreshLay.isRefreshing){
                mBinding.refreshLay.finishRefresh()
            }
        }

        lifecycleScope.launchWhenCreated {
            viewModel.mBanners.collect {
                banner.setDatas(it)
            }
        }
        lifecycleScope.launchWhenCreated {
            viewModel.projectData.collect {
                if (it.curPage == 1) mAdapter.setList(it.datas)
                else mAdapter.addData(it.datas)
                if (it.curPage == it.pageCount) mAdapter.loadMoreModule.loadMoreEnd()
                else mAdapter.loadMoreModule.loadMoreComplete()
                page = it.curPage
            }
        }
    }

    override fun lazyLoadData() {
        viewModel.getBanner()
        viewModel.getHomeList(page)
    }

    /**
     * 下拉刷新
     */
    private fun dropDownRefresh() {
        page = 0
        viewModel.getHomeList(page, true)
        viewModel.getBanner(true)
    }

    /**
     * 加载更多
     */
    private fun loadMore() {
        viewModel.getHomeList(page + 1)
    }

}

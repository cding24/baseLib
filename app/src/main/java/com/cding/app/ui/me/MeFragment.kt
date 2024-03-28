package com.cding.app.ui.me

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.cding.app.R
import com.cding.common.base.BaseVMFragment
import com.cding.app.databinding.MeFragmentBinding
import com.cding.app.ui.detail.DetailActivity

class MeFragment : BaseVMFragment<MeViewModel, MeFragmentBinding>() {

    override val layoutId get() = R.layout.me_fragment

    private val mAdapter by lazy { MeWebAdapter() }

    companion object {
        fun newInstance() = MeFragment()
    }

    override fun initView(savedInstanceState: Bundle?) {
        with(mBinding.rvMeUesdWeb) {
            layoutManager = LinearLayoutManager(context)
            adapter = mAdapter
        }
        mAdapter.setOnItemClickListener { _, _, position ->
            val intent = Intent().apply {
                setClass(requireContext(), DetailActivity::class.java)
                putExtra("url", (mAdapter.data[position]).link)
            }
            startActivity(intent)
        }

        lifecycleScope.launchWhenCreated {
            viewModel.popularWeb.collect {
                mAdapter.setList(it)
            }
        }
    }

    override fun lazyLoadData() {
        viewModel.getPopularWeb()
    }
}

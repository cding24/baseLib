package com.cding.app.ui.project

import android.content.Intent
import android.os.Bundle
import com.cding.app.R
import com.cding.common.base.BaseVMFragment
import com.cding.common.event.Message
import com.cding.app.databinding.ProjectFragmentBinding
import com.cding.app.network.entity.ArticlesBean
import com.cding.app.ui.detail.DetailActivity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview


/**
 *
 *   @author : cding
 *   @date   : 2019/11/02
 */
class ProjectFragment : BaseVMFragment<ProjectViewModel, ProjectFragmentBinding>() {
    companion object {
        fun newInstance() = ProjectFragment()
    }

    override val layoutId get() = R.layout.project_fragment


    override fun initView(savedInstanceState: Bundle?) {
        mBinding.viewModel = viewModel
    }

    @FlowPreview
    @ExperimentalCoroutinesApi
    override fun lazyLoadData() {
        viewModel.getFirstData()
    }

    override fun handleEvent(msg: Message) {
        when (msg.code) {
            0 -> {
                val bean = msg.obj as ArticlesBean
                val intent = Intent().apply {
                    setClass(requireActivity(), DetailActivity::class.java)
                    putExtra("url", bean.link)
                }
                startActivity(intent)
            }
        }
    }
}

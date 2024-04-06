package com.cding.app.ui.study

import android.os.Bundle
import com.cding.app.databinding.ActivityCoroutineStudyBinding
import com.cding.common.base.BaseActivity
import com.cding.common.base.BaseVMActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/**
 *
 * @author cding
 * @date 2024/4/2
 *
 */
class CoroutineActivity: BaseVMActivity<CoroutineViewModel, ActivityCoroutineStudyBinding>() {

    override fun initView(savedInstanceState: Bundle?) {
    }

    private fun coroutineSource(){
        val job = GlobalScope.launch(Dispatchers.IO) {

        }
        job.cancel()
        GlobalScope.launch(Dispatchers.Main) {

        }
        GlobalScope.launch(Dispatchers.Default) {

        }
        GlobalScope.launch(Dispatchers.Unconfined) {

        }
    }
}
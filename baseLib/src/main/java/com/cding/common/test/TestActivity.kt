package com.cding.common.test

import android.os.Bundle
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.cding.common.base.BaseVMActivity
import com.cding.common.databinding.ActivityTestBinding
import com.cding.common.entity.UserInfo

class TestActivity: BaseVMActivity<TestVModel, ActivityTestBinding>() {

    override fun initView(savedInstanceState: Bundle?) {
        mViewModel.login()
    }

    override fun initData() {
        super.initData()
        mViewModel.userInfo.observe(this) { userInfo ->
            Log.d("Cding", "============login userInfo: ${userInfo?.name}")
        }
    }

}
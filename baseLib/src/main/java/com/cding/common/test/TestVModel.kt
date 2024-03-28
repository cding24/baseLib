package com.cding.common.test

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.cding.common.base.BaseViewModel
import com.cding.common.entity.User
import com.cding.common.entity.UserInfo
import com.cding.common.repository.TestRepository
import kotlinx.coroutines.launch


class TestVModel : BaseViewModel() {
    private val testRepository by lazy {
        TestRepository()
    }

    val userInfo: MutableLiveData<UserInfo> = MutableLiveData()



    fun login(){
        viewModelScope.launch {
            val userRes = testRepository.login(user = User("陈华", 20))
            userInfo.postValue(
                userRes.body()
            )
        }
    }

}
package com.cding.common.base

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cding.common.app.GlobalConfig
import com.cding.common.event.Message
import com.cding.common.event.SingleLiveEvent
import kotlinx.coroutines.*


/**
 *   @author : cding
 *   @date   : 2019/11/01
 */
open class BaseViewModel : ViewModel(), IViewModel, DefaultLifecycleObserver {

    val defUI: UIChange by lazy { UIChange() }

    /**
     * 所有网络请求都在viewModelScope域中启动，当页面销毁时会自动
     * 调用ViewModel的#onCleared方法取消所有协程
     */
    fun launch(block: suspend CoroutineScope.() -> Unit) =
        viewModelScope.launch(GlobalConfig.networkException) {
            block()
        }

    /**
     * UI事件
     */
    inner class UIChange {
        val showDialogEvent by lazy { SingleLiveEvent<String>() }
        val dismissDialogEvent by lazy { SingleLiveEvent<Void>() }
        val msgEvent by lazy { SingleLiveEvent<Message>() }
    }

    override fun showLoading(text: String) {
        defUI.showDialogEvent.postValue(text)
    }

    override fun dismissLoading() {
        defUI.dismissDialogEvent.call()
    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
    }
}
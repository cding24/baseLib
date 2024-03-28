package com.cding.app.ui.study

import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.cding.app.databinding.ActivityCoroutineStudyBinding
import com.cding.app.databinding.ActivityKotlinStudyBinding
import com.cding.common.base.BaseVMActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.supervisorScope
import kotlinx.coroutines.withContext


/**
 * 协程
 *
 */
class CoroutineStudyActivity : BaseVMActivity<RxViewModel, ActivityCoroutineStudyBinding>() {

    override fun initView(savedInstanceState: Bundle?) {
    }

    private fun coroutineStudy(){
        //GlobalScope协程的生命周期和应用的周期是一样的
        val job = GlobalScope.launch {
            val job2 = launch {

            }
        }
        //job六种状态 new active completing completed canceling cancelled
        //组件销毁时，协程也销毁
        CoroutineScope(Dispatchers.IO).launch {

        }

        //public fun <T> runBlocking(context: CoroutineContext = EmptyCoroutineContext, block: suspend CoroutineScope.() -> T): T
        runBlocking {
        }


        GlobalScope.launch {
            val deffer = async(start=CoroutineStart.LAZY) {
                return@async 3
            }
            //只能放在协程里边使用，因为需要挂起协程
            val result = deffer.await()
//            deffer.start()
        }
    }

    private fun coroutineScope(){
        // GlobalScope
        // MainScope
        // runBlocking
        // coroutineScope()
        // supervisorScope {  }
        // lifecycleScope
        // viewModelScope
    }


    private fun coroutineStartMode(){
        //DEFAULT
        //ATOMIC 无法取消
        //UNDISPATCHERED 当前线程执行，知道第一个suspend调用

        //LAZY
    }

    private fun withCoroutineContext(){
        lifecycleScope.launch {
            val returnValue = withContext(Dispatchers.IO){

            }
        }
    }

}
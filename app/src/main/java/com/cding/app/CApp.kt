package com.cding.app

import android.util.Log
import com.cding.common.base.BaseApplication
import com.blankj.utilcode.util.LogUtils
import com.cding.common.BuildConfig
import com.cding.common.app.GlobalConfig
import com.scwang.smart.refresh.footer.ClassicsFooter
import com.scwang.smart.refresh.header.ClassicsHeader
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import kotlinx.coroutines.CoroutineExceptionHandler


/**
 *  @author cding
 *  @date 2019/11/04
 *
 */
class CApp : BaseApplication() {
    companion object {
        lateinit var instance: CApp
    }

    init {
        //列表刷新和加载更多的配置
        SmartRefreshLayout.setDefaultRefreshHeaderCreator { context, layout ->
            ClassicsHeader(context).setDrawableSize(20F).setEnableLastTime(false)
        }
        SmartRefreshLayout.setDefaultRefreshFooterCreator { context, layout ->
            ClassicsFooter(context).setDrawableSize(20F)
        }
    }

    override fun onCreate() {
        super.onCreate()
        instance = this

        initLog()

        //全局设置协程异常处理器，需要一个协程基类，协程lanuch的时候设置该ExceptionHandler为协程的异常处理器
        // 全局协程异常处理在META-INF/services/kotlinx.coroutines.CoroutineException文件中配置
        GlobalConfig.setNetworkException(CoroutineExceptionHandler { context, excep ->
            Log.d("Exception", "=±=±=±=±=±=Global Exception: ${excep.printStackTrace()}")
        })
    }

    private fun initLog(){
        LogUtils.getConfig().run {
            isLogSwitch = BuildConfig.DEBUG
            isLogHeadSwitch = false
            setBorderSwitch(false)
            setSingleTagSwitch(true)
        }
    }

}
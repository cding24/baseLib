package com.cding.common.base

import android.app.Application
import android.content.Context


/**
 *   @author : cding
 *   @date   : 2019/11/12
 */
open class BaseApplication : Application() {

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
    }

}
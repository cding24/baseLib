package com.cding.app.widget

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Configuration
import android.os.Build
import android.util.AttributeSet
import android.webkit.WebView


/**
 *   @auther : cding
 *   @date   : 2019/11/14
 */
@SuppressLint("ViewConstructor", "NewApi")
class CWebView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : WebView(getFixedContext(context), attrs, defStyleAttr, defStyleRes) {

    companion object {
        fun getFixedContext(context: Context): Context {
            return if (Build.VERSION.SDK_INT in 21..22) context.createConfigurationContext(
                Configuration()
            ) else context
        }
    }

}
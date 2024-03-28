package com.fubao.healthmobile.ext

import android.os.SystemClock
import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.cding.app.R
import com.cding.app.CApp

private var clickInterval = 600L
private var lastTime = 0L

    /**
     * 延迟点击
     */
    fun View.setOnIntervalClickListener(onIntervalClickListener: (View) -> Unit) {
        setOnClickListener {
            if (SystemClock.elapsedRealtime() - lastTime > clickInterval) {
                lastTime = SystemClock.elapsedRealtime()
                onIntervalClickListener.invoke(it)
            }
        }
    }

    fun ImageView.load(url: String){
        Glide.with(CApp.instance)
            .load(url)
            .centerInside()
            .placeholder(R.drawable.ic_launcher_background)
            .into(this)
    }


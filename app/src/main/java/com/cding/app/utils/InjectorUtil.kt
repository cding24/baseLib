package com.cding.app.utils

import com.cding.app.ui.home.HomeRepository

object InjectorUtil {

    fun getHomeRepository() = HomeRepository.getInstance()

}
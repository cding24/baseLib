package com.cding.app.ui.study.mqtt

data class MqttUploadBean(
    val cmd: Int,
    val deviceCode: String,
    var sendTime: String,
    var energy:Int,
    var chargeStatus:Int
)
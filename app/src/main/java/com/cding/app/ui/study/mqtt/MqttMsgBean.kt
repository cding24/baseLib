package com.cding.app.ui.study.mqtt

data class MqttMsgBean(
    val cmd: Int,
    val code: String,
    val deviceStatus: Int,
    var sendTime: String,
    var energy:Int,
    var chargeStatus:Int
)
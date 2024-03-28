package com.fubao.healthmobile.ext

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


var dateShowFormat = SimpleDateFormat("yyyy/MM/dd HH:mm", Locale.getDefault())
val dateFormater = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX") //yyyy-MM-ddTHH:mm:ss.SSSZ

fun Long.dateShowFormat() : String{
    val timeDate = Date(this)
    return dateShowFormat.format(timeDate)
}
//2020-04-09T23:00:00.000+08:00
fun Long.dateFormat() : String {
    val timeDate = Date(this)
    return dateFormater.format(timeDate)
 }

//2020-04-09T23:00:00.000+08:00
fun String.dealDateFormat(): String? {
    val df = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX") //yyyy-MM-dd'T'HH:mm:ss.SSSZ
    val date = df.parse(this)
    val df1 = SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy", Locale.UK)
    val date1 = df1.parse(date.toString())
    val df2: DateFormat = SimpleDateFormat("yyyy/MM/dd HH:mm")
    return df2.format(date1)
}
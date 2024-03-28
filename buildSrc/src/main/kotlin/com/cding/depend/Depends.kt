package com.cding.depend

/**
 *  @Author cding
 *  @Date 2021/2/5 15:28
 */
object Versions {
    const val applicationId = "com.cding.app"
    const val compileSdkVersion = 34
    const val minSdkVersion = 21
    const val targetSdkVersion = 32
    const val versionCode = 202311100
    const val versionName = "1.0.0"

    const val retrofit = "2.9.0"
    const val appcompat = "1.4.2"
    const val coreKtx = "1.8.0"
    const val material = "1.6.1"
    const val runtimeKtx = "2.5.0"
    const val espressoCore = "3.4.0"

    const val banner = "2.2.2"
    const val BRAVH = "3.0.7"
    const val coil = "2.1.0"
    const val materialDialogs = "3.1.1"
    const val utilCode = "1.30.6"
}

object AndroidX {
    const val coreKtx = "androidx.core:core-ktx:${Versions.coreKtx}"
    const val appcompat = "androidx.appcompat:appcompat:${Versions.appcompat}"
    const val material = "com.google.android.material:material:${Versions.material}"
    const val viewModelKtx = "androidx.lifecycle:lifecycle-viewmodel-ktx:2.5.0"
    const val runtimeKtx = "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.runtimeKtx}"
    const val recyclerview = "androidx.recyclerview:recyclerview:1.2.1"
    val values = arrayListOf(
        coreKtx,
        appcompat,
        material,
        viewModelKtx,
        runtimeKtx,
        recyclerview
    )
}

object Depend {
    //okhttp + retrofit
    const val okhttp = "com.squareup.okhttp3:okhttp:4.9.3"
    const val okhttpLog = "com.squareup.okhttp3:logging-interceptor:4.9.3"

    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    const val converterGson = "com.squareup.retrofit2:converter-gson:${Versions.retrofit}"
    const val converterRX = "com.squareup.retrofit2:adapter-rxjava3:${Versions.retrofit}"
    //RxJava
    const val rxjava = "io.reactivex.rxjava3:rxjava:3.1.8" //https://github.com/ReactiveX/RxJava
    const val rxAndroid = "io.reactivex.rxjava3:rxandroid:3.0.2" //https://github.com/ReactiveX/RxAndroid


    //Glide
    const val glide = "com.github.bumptech.glide:glide:4.16.0"

    const val networkCache = "com.github.AleynP:net-cache:1.0.0"
    const val espressoCore = "androidx.test.espresso:espresso-core:${Versions.espressoCore}"
    const val banner = "io.github.youth5201314:banner:${Versions.banner}"
    const val BRVAH = "com.github.CymChad:BaseRecyclerViewAdapterHelper:${Versions.BRAVH}"
    const val refreshKernel = "io.github.scwang90:refresh-layout-kernel:2.0.5"
    const val refreshHeader = "io.github.scwang90:refresh-header-classics:2.0.5"
    const val refreshFooter = "io.github.scwang90:refresh-footer-classics:2.0.5"
    const val coil = "io.coil-kt:coil:${Versions.coil}" //图片加载
    const val dialogs = "com.afollestad.material-dialogs:lifecycle:${Versions.materialDialogs}"
    const val dialogsCore = "com.afollestad.material-dialogs:core:${Versions.materialDialogs}"
    const val utilCode = "com.blankj:utilcodex:${Versions.utilCode}" //工具库
    const val bdclta = "me.tatarka.bindingcollectionadapter2:bindingcollectionadapter:4.0.0"
    const val bdcltaRv = "me.tatarka.bindingcollectionadapter2:bindingcollectionadapter-recyclerview:4.0.0"
}
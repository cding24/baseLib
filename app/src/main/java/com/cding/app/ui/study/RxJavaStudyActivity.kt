package com.cding.app.ui.study

import android.os.Bundle
import com.blankj.utilcode.util.LogUtils
import com.cding.app.databinding.ActivityReactiveExtensionsBinding
import com.cding.common.base.BaseVMActivity
import com.fubao.healthmobile.ext.setOnIntervalClickListener
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.ObservableEmitter
import io.reactivex.rxjava3.core.ObservableOnSubscribe
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.functions.Action
import io.reactivex.rxjava3.schedulers.Schedulers


class RxJavaStudyActivity: BaseVMActivity<RxViewModel, ActivityReactiveExtensionsBinding>() {

    override fun initView(savedInstancSetate: Bundle?) {
        dataBinding.btn1.setOnIntervalClickListener {
            first()
        }
    }

    override fun initData() {
        super.initData()
    }

    private fun first(){
        val observable = Observable.create(object: ObservableOnSubscribe<String>{
            override fun subscribe(emitter: ObservableEmitter<String>) {
                LogUtils.d("===============================subscribe 1")
                emitter.onNext("Hello ")
                LogUtils.d("===============================subscribe 2")
                emitter.onNext("Linghu")
                LogUtils.d("===============================subscribe 3")
                emitter.onComplete()
            }
        })
        val observer = object: Observer<String>{
            override fun onSubscribe(d: Disposable) {
                LogUtils.d("====================onSubscribe 0")
            }

            override fun onNext(t: String) {
                LogUtils.d("===============================onSubscribe 2")
            }

            override fun onError(e: Throwable) {
                LogUtils.d("===============================onSubscribe 3")
            }

            override fun onComplete() {
                LogUtils.d("===============================onSubscribe 4")
            }
        }

        //1，普通订阅
        observable.subscribe(observer)

        //2. 定义线程的订阅
        observable.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(observer)

        //3. 函数式编程
//        observable.subscribe(object: io.reactivex.rxjava3.functions.Consumer<String>{
//            override fun accept(t: String) {
//                LogUtils.d("===============================accept 11")
//            }
//        })
    }

    private fun rxJust(){
        val observable = Observable.just(1, 3,5, 7)
        observable.subscribe(object: io.reactivex.rxjava3.functions.Consumer<Int>{
                override fun accept(t: Int) {
                    LogUtils.d("===============================onNext accept 11")
                }
            }, object: io.reactivex.rxjava3.functions.Consumer<Throwable>{
                override fun accept(t: Throwable) {
                    LogUtils.d("===============================onError accept 11")
                }
            }, object: Action{
                override fun run() {
                    LogUtils.d("===============================onComplete action 11")
                }
            }
        )
    }

}
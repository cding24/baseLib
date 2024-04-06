package com.cding.app.ui.study

import android.os.Bundle
import com.blankj.utilcode.util.LogUtils
import com.cding.app.databinding.ActivityKotlinStudyBinding
import com.cding.common.base.BaseVMActivity
import kotlin.random.Random


class KotlinStudyActivity : BaseVMActivity<RxViewModel, ActivityKotlinStudyBinding>() {

    override fun initView(savedInstanceState: Bundle?) {
        val study = PersonInfo("三只牛", 28, 1)
        study.let {
            LogUtils.d("================name:${it.name}, ${it.year}")
        }
        with(study){
            "=================initView: ${name} ${year} ${sex}"
        }
        study.run {
            "hello, ${name}"
        }
        study.apply {
            name = "李铁"
        }
        study.also {
            LogUtils.d("================name:${it.name}, ${it.year}")
        }
        study.alsoFun {
            LogUtils.d("================name:${it.name}, ${it.year}")
            return
        }

        val random = Random.nextInt(100)
        val even = random.takeIf { it % 2 == 0 }
        val odd = random.takeUnless { it % 2 == 0 }
    }

    inline fun<T, R> T.letFun(block: (T) -> R): R{
        return block(this)
    }

    inline fun<T, R> withFun(receiver: T, block: T.() -> R) : R {
        return receiver.block()
    }

    inline fun<T> T.applyFun(block: T.() -> Unit) : T {
        block()
        return this
    }

    inline fun<T, R> T.runFun(block: T.() -> R): R{
        return block()
    }

    inline fun<T> T.alsoFun(block: (T) -> Unit): T{
        block(this)
        return this
    }
}

data class PersonInfo(
    var name: String,
    var year: Int,
    val sex: Int
)
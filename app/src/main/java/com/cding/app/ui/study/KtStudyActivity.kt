package com.cding.app.ui.study

import android.os.Bundle
import com.blankj.utilcode.util.LogUtils
import com.cding.app.databinding.ActivityKotlinStudyBinding
import com.cding.common.base.BaseVMActivity


class KotlinStudyActivity : BaseVMActivity<RxViewModel, ActivityKotlinStudyBinding>() {

    override fun initView(savedInstanceState: Bundle?) {
        val study = PersonInfo("三只羊", 28, 1)

        with(study){
            "=================initView: ${name} ${year} ${sex}"
        }
        study.apply {
            name = "李铁"
        }
        study.run {
            "hello, ${this.name}"
        }
        study.let {
            LogUtils.d("================name:${it.name}, ${it.year}")
        }
        study.also {
            LogUtils.d("================name:${it.name}, ${it.year}")
        }
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

    inline fun<T, R> T.letFun(block: (T) -> R): R{
        return block(this)
    }

    inline fun<T, R> T.alsoFun(block: (T) -> Unit): T{
        block(this)
        return this
    }
}

data class PersonInfo(
    var name: String,
    var year: Int,
    val sex: Int
)
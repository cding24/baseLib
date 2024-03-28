package com.cding.common.base

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ViewDataBinding
import androidx.viewbinding.ViewBinding
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import com.afollestad.materialdialogs.lifecycle.lifecycleOwner
import com.cding.common.R
import com.cding.common.event.Message
import java.lang.reflect.ParameterizedType


/**
 *  @author cding
 *  @date 2020/11/01
 *  BaseActivity
 *
 */
abstract class BaseActivity<DB : ViewBinding> : AppCompatActivity() {
    protected lateinit var dataBinding: DB
//    open val layoutId: Int = 0


    private var loadingDialog: MaterialDialog? = null


    abstract fun initView(savedInstanceState: Bundle?)

    open fun initData() {}

    open fun handleEvent(msg: Message) {}


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initDataBinding()

        initView(savedInstanceState)
        initData()
    }

    /**
     * DataBinding or ViewBinding
     */
    private fun initDataBinding() {
        val type = javaClass.genericSuperclass
        if (type is ParameterizedType) {
            val argClass = type.actualTypeArguments
                .map {
                    it as Class<*>
                }
                .first {
                    ViewBinding::class.java.isAssignableFrom(it)
                }
            when {
                ViewDataBinding::class.java.isAssignableFrom(argClass) && argClass != ViewDataBinding::class.java -> {
                    // DataBinding
                    argClass.getDeclaredMethod("inflate", LayoutInflater::class.java).let {
                        @Suppress("UNCHECKED_CAST")
                        dataBinding = it.invoke(null, layoutInflater) as DB
                        setContentView(dataBinding.root)
                        (dataBinding as ViewDataBinding).lifecycleOwner = this
                    }

//                    如果上面的方法无效，则可以使用下面注释的方法
//                    if (layoutId == 0){
//                        throw IllegalArgumentException("Using DataBinding requires overriding method layoutId")
//                    }
//                    mBinding = DataBindingUtil.setContentView(this, layoutId)
//                    (mBinding as ViewDataBinding).lifecycleOwner = this
                }
                ViewBinding::class.java.isAssignableFrom(argClass) && argClass != ViewBinding::class.java -> { //viewBinding
                    argClass.getDeclaredMethod("inflate", LayoutInflater::class.java).let {
                        @Suppress("UNCHECKED_CAST")
                        dataBinding = it.invoke(null, layoutInflater) as DB
                        setContentView(dataBinding.root)
                    }
                }
                else -> { //other
//                    if (layoutId == 0){
//                        throw IllegalArgumentException("If you don't use ViewBinding, you need to override method layoutId")
//                    }
//                    setContentView(layoutId)
                }
            }
        } else {
            throw IllegalArgumentException("Generic error")
        }
    }

    /**
     * 加载菊花
     */
    protected fun showLoading() {
        (loadingDialog ?: MaterialDialog(this)
            .cancelable(false)
            .cornerRadius(8f)
            .customView(R.layout.custom_progress_dialog_view, noVerticalPadding = true)
            .lifecycleOwner(this)
            .maxWidth(R.dimen.dialog_width).also {
                loadingDialog = it
            })
            .show()
    }

    /**
     * 关闭加载菊花
     */
    protected fun dismissLoading() {
        loadingDialog?.run {
            if (isShowing){
                dismiss()
            }
        }
    }

}
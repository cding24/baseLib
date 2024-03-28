package com.cding.common.base

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.viewbinding.ViewBinding
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import com.afollestad.materialdialogs.lifecycle.lifecycleOwner
import com.cding.common.event.Message
import com.cding.common.R
import java.lang.reflect.ParameterizedType

/**
 *  @author cding
 *  @date 2019/11/01
 */
abstract class BaseFragment<VB : ViewBinding> : Fragment() {

    //使用DataBinding时, 要重写此方法返回相应的布局id, 使用ViewBinding时，不用重写此方法
    @LayoutRes
    open val layoutId = 0

    private lateinit var _binding: VB
    protected val mBinding get() = _binding

    private var dialog: MaterialDialog? = null
    //是否第一次加载
    private var isFirst: Boolean = true


    open fun initView(savedInstanceState: Bundle?) {}

    open fun initObserve() {}

    //懒加载
    open fun lazyLoadData() {}

    open fun handleEvent(msg: Message) {}


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return initBinding(inflater, container)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initView(savedInstanceState)
        onVisible()
        initObserve()
        Log.d("BaseFragment", "==========onViewCreated")
    }

    override fun onResume() {
        super.onResume()
        onVisible()
    }

    private fun initBinding(inflater: LayoutInflater, container: ViewGroup?): View {
        val type = javaClass.genericSuperclass
        if (type is ParameterizedType) {
            val argClass = type.actualTypeArguments[1] as Class<*>
            return when {
                ViewDataBinding::class.java.isAssignableFrom(argClass) && argClass != ViewDataBinding::class.java -> {
                    // DataBinding
                    argClass.getDeclaredMethod("inflate", LayoutInflater::class.java).let {
                        @Suppress("UNCHECKED_CAST")
                        _binding = it.invoke(null, layoutInflater) as VB
                        (_binding as ViewDataBinding).lifecycleOwner = this

                        mBinding.root
                    }

//                    if (layoutId == 0){
//                        throw IllegalArgumentException("Using DataBinding requires overriding method layoutId")
//                    }
//                    Log.d("BaseFragment", "==========initBinding inflater:${inflater==null}, layoutId:${layoutId}, container:${container==null}")
//                    _binding = DataBindingUtil.inflate(inflater, layoutId, container, false)
//                    (mBinding as ViewDataBinding).lifecycleOwner = this
//                    mBinding.root
                }
                ViewBinding::class.java.isAssignableFrom(argClass) && argClass != ViewBinding::class.java -> {
                    argClass.getDeclaredMethod("inflate", LayoutInflater::class.java).let {
                        @Suppress("UNCHECKED_CAST")
                        _binding = it.invoke(null, inflater) as VB

                        mBinding.root
                    }
                }
                else -> {
                    if (layoutId == 0){
                        throw IllegalArgumentException("If you don't use ViewBinding, you need to override method layoutId")
                    }
                    inflater.inflate(layoutId, container, false)
                }
            }
        } else{
            throw IllegalArgumentException("Generic error")
        }
    }

    /**
     * 是否需要懒加载
     */
    private fun onVisible() {
        if (lifecycle.currentState == Lifecycle.State.STARTED && isFirst) {
            lazyLoadData()
            isFirst = false
        }
    }

    /**
     * 打开等待框
     */
    protected fun showLoading() {
        (dialog ?: MaterialDialog(requireContext())
            .cancelable(false)
            .cornerRadius(8f)
            .customView(R.layout.custom_progress_dialog_view, noVerticalPadding = true)
            .lifecycleOwner(this)
            .maxWidth(R.dimen.dialog_width)
            .also {
                dialog = it
            }).show()
    }

    /**
     * 关闭等待框
     */
    protected fun dismissLoading() {
        dialog?.run { if (isShowing) dismiss() }
    }

}
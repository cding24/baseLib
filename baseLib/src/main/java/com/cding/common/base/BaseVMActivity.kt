package com.cding.common.base

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import java.lang.reflect.ParameterizedType


/**
 * @author cding
 * @date 2022/07/31
 *
 */
abstract class BaseVMActivity<VM: BaseViewModel, VB: ViewBinding> : BaseActivity<VB>() {
    protected lateinit var mViewModel: VM

    override fun onCreate(savedInstanceState: Bundle?) {
        initViewModel()
        lifecycle.addObserver(mViewModel)
        //注册 UI事件
        registerDefUIChange()
        super.onCreate(savedInstanceState)
    }

    /**
     * 创建 ViewModel
     */
    private fun initViewModel(){
        mViewModel = ViewModelProvider(this).get(getVMClazz(this))
    }

    /**
     * 获取当前类绑定的泛型ViewModel-clazz
     */
    fun <VM> getVMClazz(obj: Any): VM {
        return (obj.javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0] as VM
    }

    /**
     * 注册 UI 事件
     */
    private fun registerDefUIChange() {
        mViewModel.defUI.showDialogEvent.observe(this) {
            showLoading()
        }
        mViewModel.defUI.dismissDialogEvent.observe(this) {
            dismissLoading()
        }
        mViewModel.defUI.msgEvent.observe(this) {
            handleEvent(it)
        }
    }

}
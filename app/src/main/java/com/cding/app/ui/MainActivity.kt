package com.cding.app.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import com.cding.common.base.BaseVMActivity
import com.cding.common.base.NoViewModel
import com.blankj.utilcode.util.PermissionUtils
import com.cding.app.R
import com.cding.app.databinding.ActivityMainBinding
import com.cding.app.ui.home.HomeFragment
import com.cding.app.ui.me.MeFragment
import com.cding.app.ui.project.ProjectFragment
import com.cding.app.ui.study.ConstuctorPerson


/**
 * @author cding
 * @date 2019/3/3
 */
class MainActivity: BaseVMActivity<NoViewModel, ActivityMainBinding>() {

    private val fragments = ArrayList<Fragment>()
    private lateinit var showFragment: Fragment

    override fun initView(savedInstanceState: Bundle?) {
        fragments.add(HomeFragment.newInstance())
        fragments.add(ProjectFragment.newInstance())
        fragments.add(MeFragment.newInstance())
        showFragment = fragments[0]
        supportFragmentManager.beginTransaction()
            .replace(R.id.layContainer, showFragment)
            .commitNow()

        dataBinding.bottomNavigation.setOnItemSelectedListener {
            changePage(it.itemId)
            return@setOnItemSelectedListener true
        }

        //权限
        PermissionUtils.permission(*PermissionUtils.getPermissions().toTypedArray())
            .callback(object: PermissionUtils.FullCallback {
                override fun onGranted(granted: MutableList<String>) {
                }

                override fun onDenied(forever: MutableList<String>, denied: MutableList<String>) {
                }
            })
            .request()
    }

    override fun initData() {
    }

    private fun changePage(itemId: Int) {
        val index = when (itemId) {
            R.id.action_home -> 0
            R.id.action_project -> 1
            R.id.action_me -> 2
            else -> return
        }
        val now = fragments[index]
        supportFragmentManager.beginTransaction().apply {
            if (!now.isAdded) {
                add(R.id.layContainer, now)
            }
            hide(showFragment)
            show(now)

            showFragment = now
            commit()
        }
    }

}
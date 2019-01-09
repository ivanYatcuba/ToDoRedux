package com.lanars.todoredux.flow.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

abstract class BaseActivity : AppCompatActivity(), HasSupportFragmentInjector {
    var currentFragment: BaseFragment? = null

    @Suppress("MemberVisibilityCanBePrivate")
    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportFragmentManager.registerFragmentLifecycleCallbacks(object : FragmentManager.FragmentLifecycleCallbacks() {
            override fun onFragmentResumed(fm: FragmentManager, fragment: Fragment) {
                super.onFragmentResumed(fm, fragment)
                if (fragment is BaseFragment) {
                    return
                }
                if (fragment is BaseFragment) {
                    currentFragment = fragment
                }
            }
        }, true)
    }

    override fun onDestroy() {
        super.onDestroy()
        currentFragment = null
    }

    override fun supportFragmentInjector(): AndroidInjector<Fragment> = dispatchingAndroidInjector
}
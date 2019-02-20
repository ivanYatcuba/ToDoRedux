package com.lanars.todoredux.android.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.lanars.todoredux.AppAction
import com.lanars.todoredux.AppState
import com.lanars.todoredux.redux.store.ReduxStore
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

abstract class BaseActivity : AppCompatActivity(), HasSupportFragmentInjector, ReduxStore.Subscriber<AppState> {
    var currentFragment: BaseFragment? = null

    @Suppress("MemberVisibilityCanBePrivate")
    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    @Inject
    lateinit var reduxStore: ReduxStore<AppState, AppAction>

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

        onNewState(reduxStore.getState())

        reduxStore.subscribe(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        currentFragment = null
        reduxStore.unsubscribe(this)
    }

    override fun supportFragmentInjector(): AndroidInjector<Fragment> = dispatchingAndroidInjector
}
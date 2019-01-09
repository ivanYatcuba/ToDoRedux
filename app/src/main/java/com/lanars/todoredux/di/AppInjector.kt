package com.lanars.todoredux.di

import android.app.Activity
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.lanars.todoredux.ReduxApplication
import com.lanars.todoredux.di.component.DaggerAppComponent
import dagger.android.AndroidInjection
import dagger.android.support.AndroidSupportInjection


class AppInjector {

    companion object {

        fun init(app: ReduxApplication) {
            DaggerAppComponent
                    .builder()
                    .application(app)
                    .build()
                    .inject(app)

            app.registerActivityLifecycleCallbacks(object : AppActivityLifecycleCallbacks() {
                override fun onActivityCreated(activity: Activity?, savedInstanceState: Bundle?) {
                    activity?.let {
                        handleActivity(it)
                    }
                }
            })
        }

        fun handleActivity(activity: Activity) {

            if (activity is AppCompatActivity) {
                AndroidInjection.inject(activity)
                activity.supportFragmentManager
                        .registerFragmentLifecycleCallbacks(object : FragmentManager.FragmentLifecycleCallbacks() {
                            override fun onFragmentCreated(fm: FragmentManager, f: Fragment, savedInstanceState: Bundle?) {
                                if (f is Injectable) {
                                    AndroidSupportInjection.inject(f)
                                }
                            }
                        }, true)
            }
        }
    }
}
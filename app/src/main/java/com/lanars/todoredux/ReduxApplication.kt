package com.lanars.todoredux

import android.app.Activity
import android.app.Application
import com.lanars.todoredux.di.AppInjector
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject

class ReduxApplication : Application(), HasActivityInjector {

    @Suppress("MemberVisibilityCanBePrivate")
    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    override fun onCreate() {
        super.onCreate()
        AppInjector.init(this)
    }

    override fun activityInjector(): AndroidInjector<Activity> = dispatchingAndroidInjector

}
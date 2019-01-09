package com.lanars.todoredux.di.module

import com.lanars.todoredux.flow.main.MainFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Suppress("unused")
@Module
abstract class MainModule {

    @ContributesAndroidInjector
    abstract fun contributeMainFragment(): MainFragment

}
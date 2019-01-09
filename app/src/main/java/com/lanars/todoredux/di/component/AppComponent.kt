package com.lanars.todoredux.di.component

import android.app.Application
import com.lanars.todoredux.ReduxApplication
import com.lanars.todoredux.di.module.ActivityModule
import com.lanars.todoredux.di.module.MainModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
    AndroidInjectionModule::class,
    MainModule::class,
    ActivityModule::class])
interface AppComponent {

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): Builder

        fun build(): AppComponent
    }

    fun inject(app: ReduxApplication)
}
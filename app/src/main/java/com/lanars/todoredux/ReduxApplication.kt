package com.lanars.todoredux

import android.app.Application
import com.lanars.todoredux.di.appComponent
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class ReduxApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@ReduxApplication)
            modules(appComponent)
        }
    }
}
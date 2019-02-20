package com.lanars.todoredux.android.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.lanars.todoredux.AppAction
import com.lanars.todoredux.AppState
import com.lanars.todoredux.redux.store.ReduxStore
import org.koin.android.ext.android.inject

abstract class BaseActivity : AppCompatActivity(), ReduxStore.Subscriber<AppState> {
    val reduxStore: ReduxStore<AppState, AppAction> by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onNewState(reduxStore.getState())
        reduxStore.subscribe(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        reduxStore.unsubscribe(this)
    }
}
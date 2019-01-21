package com.lanars.todoredux.redux.middleware

import android.util.Log
import com.lanars.todoredux.AppAction
import com.lanars.todoredux.AppState
import com.lanars.todoredux.redux.store.ReduxStore

class ActionLoggerMiddleware : ReduxMiddleware<AppState, AppAction, ReduxStore<AppState, AppAction>>() {
    override fun doBeforeDispatch(store: ReduxStore<AppState, AppAction>, action: AppAction) {
        Log.d("====ACTION===", "=============================")
        Log.d("====ACTION===", action.javaClass.canonicalName + ": " + action.toString())
        Log.d("====ACTION===", "=============================")
    }

    override fun doAfterDispatch(store: ReduxStore<AppState, AppAction>, action: AppAction) {
    }
}
package com.lanars.todoredux.redux.middleware.epic

import com.lanars.todoredux.AppAction
import com.lanars.todoredux.AppState
import com.lanars.todoredux.redux.store.ReduxStore
import io.reactivex.Observable
import java.util.concurrent.TimeUnit

class DelayedToastEpic : Epic<AppState, AppAction> {

    override fun map(actions: Observable<out AppAction>, store: ReduxStore<AppState, AppAction>): Observable<out AppAction> {
        return actions.filter { it is AppAction.DisplayToast }.delay(1000, TimeUnit.MILLISECONDS).map { AppAction.DisplayToastLong("Epic HI!!!") }
    }
}
package com.lanars.todoredux.di.module

import com.lanars.todoredux.AppAction
import com.lanars.todoredux.AppState
import com.lanars.todoredux.redux.AppReducer
import com.lanars.todoredux.redux.middleware.ActionLoggerMiddleware
import com.lanars.todoredux.redux.middleware.epic.DelayedToastEpic
import com.lanars.todoredux.redux.middleware.epic.combineEpics
import com.lanars.todoredux.redux.middleware.epic.createEpicMiddleware
import com.lanars.todoredux.redux.store.ReduxStore
import com.lanars.todoredux.redux.store.Store
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ReduxModule {

    @Singleton
    @Provides
    fun provideReduxReducer(): AppReducer {
        return AppReducer()
    }

    @Singleton
    @Provides
    fun provideReduxStore(reducer: AppReducer): ReduxStore<AppState, AppAction> {
        val store = Store(currentState = AppState.initialState, reducer = { state, action: AppAction ->
            reducer.reduce(action = action, state = state)
        })
        store.applyMiddleware(createEpicMiddleware(combineEpics(DelayedToastEpic()), store), ActionLoggerMiddleware())
        return store
    }

}
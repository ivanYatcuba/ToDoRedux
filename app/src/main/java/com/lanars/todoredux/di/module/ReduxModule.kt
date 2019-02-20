package com.lanars.todoredux.di.module

import com.lanars.todoredux.AppAction
import com.lanars.todoredux.AppState
import com.lanars.todoredux.ReduxApplication
import com.lanars.todoredux.flow.main.epic.LoadToDoEpic
import com.lanars.todoredux.flow.main.epic.PatchToDoEpic
import com.lanars.todoredux.redux.AppReducer
import com.lanars.todoredux.redux.middleware.ActionLoggerMiddleware
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
    fun provideReduxStore(application: ReduxApplication,
                          reducer: AppReducer,
                          loadToDoEpic: LoadToDoEpic,
                          patchToDoEpic: PatchToDoEpic): ReduxStore<AppState, AppAction> {
        val store = Store(currentState = AppState.getInitialState(application), reducer = { state, action: AppAction ->
            reducer.reduce(action = action, state = state)
        })
        store.applyMiddleware(createEpicMiddleware(combineEpics(loadToDoEpic, patchToDoEpic), store), ActionLoggerMiddleware())
        return store
    }
}
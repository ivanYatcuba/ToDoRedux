package com.lanars.todoredux.redux.store

import com.lanars.todoredux.redux.ReduxAction
import com.lanars.todoredux.redux.ReduxMiddleware
import com.lanars.todoredux.redux.ReduxState

interface ReduxStore<S : ReduxState, A : ReduxAction> {

    fun getState(): S

    fun replaceReducer(reducer: (state: S, action: A) -> S)

    fun dispatch(action: A)

    fun applyMiddleware(vararg middlewareList: ReduxMiddleware<S, A, ReduxStore<S, A>>)

}
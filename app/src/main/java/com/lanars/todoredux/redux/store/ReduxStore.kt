package com.lanars.todoredux.redux.store

import com.lanars.todoredux.redux.ReduxAction
import com.lanars.todoredux.redux.ReduxState
import com.lanars.todoredux.redux.middleware.ReduxMiddleware

interface ReduxStore<S : ReduxState, A : ReduxAction> {

    fun getState(): S

    fun replaceReducer(reducer: (state: S, action: A) -> S)

    fun dispatch(action: A)

    fun applyMiddleware(vararg middlewareList: ReduxMiddleware<S, A, ReduxStore<S, A>>)

    fun subscribe(subscriber: Subscriber<S>)

    fun unsubscribe(subscriber: Subscriber<S>)

    interface Subscriber<in S> {
        fun onNewState(state: S)
    }
}
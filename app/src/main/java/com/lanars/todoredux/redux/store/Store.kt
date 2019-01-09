package com.lanars.todoredux.redux.store

import com.lanars.todoredux.redux.ReduxAction
import com.lanars.todoredux.redux.ReduxMiddleware
import com.lanars.todoredux.redux.ReduxState

open class Store<S : ReduxState, A : ReduxAction>(
        protected open var currentState: S,
        open var reducer: (state: S, action: A) -> S
) : ReduxStore<S, A> {

    private var dispatchFun: (A) -> Unit = { action: A -> internalDispatch(action) }

    override fun getState(): S = currentState

    override fun replaceReducer(reducer: (state: S, action: A) -> S) {
        this.reducer = reducer
    }

    override fun dispatch(action: A) = dispatchFun(action)

    protected open fun internalDispatch(action: A) {
        currentState = reducer(currentState, action)
    }

    override fun applyMiddleware(vararg middlewareList: ReduxMiddleware<S, A, ReduxStore<S, A>>) {
        middlewareList.forEach { middleware ->
            dispatchFun = middleware.apply(this)(dispatchFun)
        }
    }

}
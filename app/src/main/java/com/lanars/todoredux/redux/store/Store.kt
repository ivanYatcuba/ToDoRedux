package com.lanars.todoredux.redux.store

import com.lanars.todoredux.redux.ReduxAction
import com.lanars.todoredux.redux.ReduxState
import com.lanars.todoredux.redux.middleware.ReduxMiddleware
import io.reactivex.BackpressureStrategy
import io.reactivex.disposables.Disposable
import io.reactivex.subjects.PublishSubject

open class Store<S : ReduxState, A : ReduxAction>(
        protected open var currentState: S,
        open var reducer: (state: S, action: A) -> S
) : ReduxStore<S, A> {

    private val stateSubject = PublishSubject.create<S>()
    private val subscriptions = HashMap<ReduxStore.Subscriber<S>, Disposable>()

    private var dispatchFun: (A) -> Unit = { action: A -> internalDispatch(action) }

    override fun getState(): S = currentState

    override fun replaceReducer(reducer: (state: S, action: A) -> S) {
        this.reducer = reducer
    }

    override fun dispatch(action: A) = dispatchFun(action)

    protected open fun internalDispatch(action: A) {
        currentState = reducer(currentState, action)
        stateSubject.onNext(currentState)
    }

    override fun applyMiddleware(vararg middlewareList: ReduxMiddleware<S, A, ReduxStore<S, A>>) {
        middlewareList.forEach { middleware ->
            dispatchFun = middleware.apply(this)(dispatchFun)
        }
    }

    override fun subscribe(subscriber: ReduxStore.Subscriber<S>) {
        if (!subscriptions.containsKey(subscriber)) {
            val disposable = stateSubject.toFlowable(BackpressureStrategy.BUFFER).subscribe {
                subscriber.onNewState(it)
            }
            subscriptions[subscriber] = disposable
        }
    }

    override fun unsubscribe(subscriber: ReduxStore.Subscriber<S>) {
        val disposable = subscriptions[subscriber]
        disposable?.dispose()
        subscriptions.remove(subscriber)
    }
}
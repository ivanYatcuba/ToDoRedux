package com.lanars.todoredux.redux.middleware.epic

import com.lanars.todoredux.redux.ReduxAction
import com.lanars.todoredux.redux.ReduxState
import com.lanars.todoredux.redux.store.ReduxStore
import io.reactivex.Observable

interface Epic<S : ReduxState, A : ReduxAction> {

    fun map(actions: Observable<out A>, store: ReduxStore<S, A>): Observable<out A>

    companion object {
        operator fun <S : ReduxState, A : ReduxAction> invoke(f: (Observable<out A>, ReduxStore<S, A>) -> Observable<out A>) = object : Epic<S, A> {
            override fun map(actions: Observable<out A>, store: ReduxStore<S, A>): Observable<out A> = f(actions, store)
        }
    }
}

fun <S : ReduxState, A : ReduxAction> combineEpics(vararg epics: Epic<S, A>): Epic<S, A> {
    return object : Epic<S, A> {
        override fun map(actions: Observable<out A>, store: ReduxStore<S, A>): Observable<out A> {
            return Observable.merge(epics.map { it.map(actions, store) })
        }
    }
}
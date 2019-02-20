package com.lanars.todoredux.redux.middleware.epic

import com.lanars.todoredux.redux.ReduxAction
import com.lanars.todoredux.redux.ReduxState
import com.lanars.todoredux.redux.middleware.ReduxMiddleware
import com.lanars.todoredux.redux.store.ReduxStore
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.BehaviorSubject
import io.reactivex.subjects.PublishSubject

abstract class EpicMiddleware<S : ReduxState, A : ReduxAction> : ReduxMiddleware<S, A, ReduxStore<S, A>>() {
    abstract fun replaceEpic(epic: Epic<S, A>)
}

fun <S : ReduxState, A : ReduxAction> createEpicMiddleware(epic: Epic<S, A>, store: ReduxStore<S, A>): EpicMiddleware<S, A> {
    return object : EpicMiddleware<S, A>() {

        private val actions = PublishSubject.create<A>()
        private val epics = BehaviorSubject.createDefault(epic)

        init {
            epics.switchMap { it.map(actions.subscribeOn(Schedulers.io()), store) }.subscribe { store.dispatch(it) }
        }

        override fun doBeforeDispatch(store: ReduxStore<S, A>, action: A) {
        }

        override fun doAfterDispatch(store: ReduxStore<S, A>, action: A) {
            actions.onNext(action)
        }

        override fun replaceEpic(epic: Epic<S, A>) {
            epics.onNext(epic)
        }
    }
}
package com.lanars.todoredux.flow.main.epic

import com.lanars.todoredux.AppAction
import com.lanars.todoredux.AppState
import com.lanars.todoredux.flow.main.ToDoRepository
import com.lanars.todoredux.redux.middleware.epic.Epic
import com.lanars.todoredux.redux.store.ReduxStore
import io.reactivex.Observable
import io.reactivex.functions.Function
import io.reactivex.schedulers.Schedulers

class LoadToDoEpic(private val toDoRepository: ToDoRepository) : Epic<AppState, AppAction> {

    override fun map(actions: Observable<out AppAction>, store: ReduxStore<AppState, AppAction>): Observable<out AppAction> {
        return actions
                .filter { it is AppAction.LoadToDosAction }
                .flatMap {
                    toDoRepository.loadToDos()
                            .subscribeOn(Schedulers.io())
                            .toObservable()
                            .map { totdos -> AppAction.ToDosLoadedAction(totdos.subList(0, 50), null) }
                            .onErrorResumeNext(Function { t -> Observable.just(AppAction.ToDosLoadedAction(null, t)) })
                }
    }
}
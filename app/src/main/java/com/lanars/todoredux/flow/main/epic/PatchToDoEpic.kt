package com.lanars.todoredux.flow.main.epic

import com.lanars.todoredux.AppAction
import com.lanars.todoredux.AppState
import com.lanars.todoredux.flow.main.ToDoRepository
import com.lanars.todoredux.redux.middleware.epic.Epic
import com.lanars.todoredux.redux.store.ReduxStore
import io.reactivex.Observable
import io.reactivex.functions.Function
import io.reactivex.schedulers.Schedulers

class PatchToDoEpic(private val toDoRepository: ToDoRepository) : Epic<AppState, AppAction> {

    override fun map(actions: Observable<out AppAction>, store: ReduxStore<AppState, AppAction>): Observable<out AppAction> {
        return actions
                .filter { it is AppAction.ToggleToDoComplete }
                .flatMap {
                    val originalToDo = (it as AppAction.ToggleToDoComplete).todo
                    val toggledToDo = originalToDo.copy(completed = !originalToDo.completed)
                    val itemIndex = it.itemIndex
                    toDoRepository.patchToDo(toggledToDo.id, toggledToDo)
                            .subscribeOn(Schedulers.io())
                            .toObservable()
                            .map { AppAction.ToDosChangedAction(it, null, itemIndex) }
                            .onErrorResumeNext(Function { t -> Observable.just(AppAction.ToDosChangedAction(null, t, -1)) })
                }

    }
}
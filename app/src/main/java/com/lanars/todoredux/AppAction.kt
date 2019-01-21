package com.lanars.todoredux

import com.lanars.todoredux.flow.main.model.ToDo
import com.lanars.todoredux.redux.ReduxAction

sealed class AppAction : ReduxAction {

    class LoadToDosAction : AppAction()

    class ToDosLoadedAction(val todos: List<ToDo>?, val error: Throwable?) : AppAction()

    class ToDosChangedAction(val todo: ToDo?, val error: Throwable?, val changeId: Int) : AppAction()

    class ToggleToDoComplete(val todo: ToDo, val itemIndex: Int) : AppAction()

}


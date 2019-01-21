package com.lanars.todoredux

import com.lanars.todoredux.flow.main.model.ToDo
import com.lanars.todoredux.redux.ReduxAction

sealed class AppAction : ReduxAction {

    class LoadToDosAction : AppAction()

    class ToDosLoadedAction(val todos: List<ToDo>?, val error: Throwable?) : AppAction()

    class ErrorDispatchAction(val throwable: Throwable) : AppAction()


}


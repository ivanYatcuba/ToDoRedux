package com.lanars.todoredux

import com.lanars.todoredux.redux.ReduxAction

sealed class AppAction : ReduxAction {

    class DisplayToast(val data: String) : AppAction()

    class DisplayToastLong(val data: String) : AppAction()
}
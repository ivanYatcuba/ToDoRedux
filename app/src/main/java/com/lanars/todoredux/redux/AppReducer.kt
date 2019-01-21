package com.lanars.todoredux.redux

import com.lanars.todoredux.AppAction
import com.lanars.todoredux.AppState

class AppReducer {

    fun reduce(action: AppAction, state: AppState): AppState {
        if (action is AppAction.DisplayToast) {
            state.mainState.message = action.data
        }
        if (action is AppAction.DisplayToastLong) {
            state.mainState.message = action.data
        }
        return state
    }
}
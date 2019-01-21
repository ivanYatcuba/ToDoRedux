package com.lanars.todoredux.redux

import com.lanars.todoredux.AppAction
import com.lanars.todoredux.AppState

class AppReducer {

    fun reduce(action: AppAction, state: AppState): AppState {
        if (action is AppAction.ToDosLoadedAction) {
            state.mainState.todos = action.todos
            state.mainState.error = action.error
        }
        return state
    }
}
package com.lanars.todoredux.redux

import com.lanars.todoredux.AppAction
import com.lanars.todoredux.AppState

class AppReducer {

    fun reduce(action: AppAction, state: AppState): AppState {
        if (action is AppAction.ToDosLoadedAction) {
            state.mainState.todos = action.todos?.toMutableList()
            state.mainState.error = action.error
        }

        if (action is AppAction.ToDosChangedAction) {
            action.todo?.let { todo ->
                if (action.changeId > -1) {
                    val list = state.mainState.todos?.toMutableList()
                    list?.set(action.changeId, todo)
                    state.mainState.todos = list
                }
            }
            state.mainState.changedItemIndex = action.changeId
            state.mainState.error = action.error
        }
        return state
    }
}
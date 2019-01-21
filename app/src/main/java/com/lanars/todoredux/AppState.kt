package com.lanars.todoredux

import com.lanars.todoredux.flow.main.MainState
import com.lanars.todoredux.redux.ReduxState

data class AppState(val mainState: MainState) : ReduxState {
    companion object {
        val initialState = AppState(MainState())
    }
}
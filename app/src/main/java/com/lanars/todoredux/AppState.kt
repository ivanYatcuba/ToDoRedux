package com.lanars.todoredux

import android.app.Application
import android.preference.PreferenceManager
import com.lanars.todoredux.flow.main.model.Theme
import com.lanars.todoredux.flow.main.model.ToDo
import com.lanars.todoredux.redux.ReduxState
import java.util.*

data class AppState(val mainState: MainState, var theme: String = Theme.GREEN_THEME.name) : ReduxState {

    companion object {

        fun getInitialState(app: Application): AppState {
            val theme = PreferenceManager
                    .getDefaultSharedPreferences(app)
                    .getString(APP_THEME, Theme.GREEN_THEME.name)

            return AppState(MainState(), theme = theme)
        }

        const val APP_THEME = "app_theme"
    }
}

data class MainState(var todos: MutableList<ToDo>? = LinkedList(),
                     var error: Throwable? = null,
                     var changedItemIndex: Int = -1)
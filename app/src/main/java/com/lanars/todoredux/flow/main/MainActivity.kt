package com.lanars.todoredux.flow.main

import android.os.Bundle
import com.lanars.todoredux.AppState
import com.lanars.todoredux.R
import com.lanars.todoredux.android.base.BaseActivity
import com.lanars.todoredux.flow.main.model.Theme

class MainActivity : BaseActivity() {

    var theme: Theme? = Theme.GREEN_THEME

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setCurrentTheme(Theme.valueOf(reduxStore.getState().theme))
        setContentView(R.layout.activity_main)
    }

    override fun onNewState(state: AppState) {
        val theme = Theme.valueOf(state.theme)

        if (theme != this.theme) {
            setCurrentTheme(theme)
            runOnUiThread {
                recreate()
            }
        }
    }

    private fun setCurrentTheme(theme: Theme) {
        when (theme) {
            Theme.GREEN_THEME -> setTheme(R.style.GreenAppTheme)
            Theme.DARK_BLUE_THEME -> setTheme(R.style.BlueAppTheme)
        }
        this.theme = theme
    }
}

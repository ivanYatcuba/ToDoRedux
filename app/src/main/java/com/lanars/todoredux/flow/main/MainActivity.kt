package com.lanars.todoredux.flow.main

import android.os.Bundle
import android.preference.PreferenceManager
import com.lanars.todoredux.R
import com.lanars.todoredux.android.base.BaseActivity

class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val useDark = PreferenceManager
                .getDefaultSharedPreferences(this)
                .getBoolean(USE_DARK_THEME, false)
        if (useDark) {
            setTheme(R.style.BlueAppTheme)
        } else {
            setTheme(R.style.GreenAppTheme)
        }
        setContentView(R.layout.activity_main)
    }

    companion object {
        const val USE_DARK_THEME = "use_dark"
    }
}

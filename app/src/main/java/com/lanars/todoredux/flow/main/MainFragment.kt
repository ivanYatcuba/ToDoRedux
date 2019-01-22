package com.lanars.todoredux.flow.main

import android.os.Bundle
import android.preference.PreferenceManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.lanars.todoredux.AppAction
import com.lanars.todoredux.AppState
import com.lanars.todoredux.R
import com.lanars.todoredux.android.base.BaseFragment
import com.lanars.todoredux.flow.main.MainActivity.Companion.USE_DARK_THEME
import kotlinx.android.synthetic.main.fragment_main.*


class MainFragment : BaseFragment() {

    private val adapter: ToDoRecyclerViewAdapter by lazy {
        ToDoRecyclerViewAdapter { todo, position ->
            reduxStore.dispatch(AppAction.ToggleToDoComplete(todo, position))
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        reduxStore.dispatch(AppAction.LoadToDosAction())
    }

    override fun initViewListeners() {
        rvToDos.adapter = adapter
        bgreenTheme.setOnClickListener {
            PreferenceManager.getDefaultSharedPreferences(activity).edit().putBoolean(USE_DARK_THEME, false).commit()
            activity?.recreate()
        }
        bblueTheme.setOnClickListener {
            PreferenceManager.getDefaultSharedPreferences(activity).edit().putBoolean(USE_DARK_THEME, true).commit()
            activity?.recreate()
        }
    }

    override fun onNewState(state: AppState) {
        activity?.runOnUiThread {
            state.mainState.todos?.let {
                if (state.mainState.changedItemIndex > -1) {
                    Toast.makeText(activity, "Item updated!", Toast.LENGTH_LONG).show()
                }
                adapter.setItems(it)
            }
            state.mainState.error?.let {
                Toast.makeText(activity, it.localizedMessage, Toast.LENGTH_LONG).show()
            }
        }
    }
}
package com.lanars.todoredux.flow.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.lanars.todoredux.AppAction
import com.lanars.todoredux.AppState
import com.lanars.todoredux.R
import com.lanars.todoredux.android.base.BaseFragment
import com.lanars.todoredux.flow.main.model.Theme
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
            reduxStore.dispatch(AppAction.ChangeThemeAction(Theme.GREEN_THEME))
        }
        bblueTheme.setOnClickListener {
            reduxStore.dispatch(AppAction.ChangeThemeAction(Theme.DARK_BLUE_THEME))
        }
    }

    override fun onNewState(state: AppState) {
        activity?.runOnUiThread {
            state.mainState.todos?.let {
                if (state.mainState.changedItemIndex > -1) {
                    Toast.makeText(activity, getString(R.string.toast_item_updated), Toast.LENGTH_LONG).show()
                }
                adapter.setItems(it)
            }
            state.mainState.error?.let {
                Toast.makeText(activity, it.localizedMessage, Toast.LENGTH_LONG).show()
            }
        }
    }
}
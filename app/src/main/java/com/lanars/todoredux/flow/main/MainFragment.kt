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
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : BaseFragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun initViewListeners() {
        btnAction.setOnClickListener {
            reduxStore.dispatch(AppAction.DisplayToast("Hi!"))
        }
    }

    override fun onNewState(state: AppState) {
        activity?.runOnUiThread {
            Toast.makeText(activity, state.mainState.message, Toast.LENGTH_LONG).show()
        }
    }
}
package com.lanars.todoredux.android.base

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.lanars.todoredux.AppAction
import com.lanars.todoredux.AppState
import com.lanars.todoredux.redux.store.ReduxStore
import org.koin.android.ext.android.inject

abstract class BaseFragment : Fragment(), ReduxStore.Subscriber<AppState> {

    val reduxStore: ReduxStore<AppState, AppAction> by inject()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initViewListeners()

        onNewState(reduxStore.getState())

        reduxStore.subscribe(this)
    }

    /**
     * Initialize here only listeners
     * State must be initialized only in onNewState
     */
    abstract fun initViewListeners()

    override fun onDestroyView() {
        reduxStore.unsubscribe(this)
        super.onDestroyView()
    }
}
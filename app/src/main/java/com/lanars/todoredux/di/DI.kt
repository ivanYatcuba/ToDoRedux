package com.lanars.todoredux.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.lanars.todoredux.AppAction
import com.lanars.todoredux.AppState
import com.lanars.todoredux.flow.main.ToDoRepository
import com.lanars.todoredux.flow.main.epic.LoadToDoEpic
import com.lanars.todoredux.flow.main.epic.PatchToDoEpic
import com.lanars.todoredux.redux.AppReducer
import com.lanars.todoredux.redux.middleware.ActionLoggerMiddleware
import com.lanars.todoredux.redux.middleware.epic.combineEpics
import com.lanars.todoredux.redux.middleware.epic.createEpicMiddleware
import com.lanars.todoredux.redux.store.ReduxStore
import com.lanars.todoredux.redux.store.Store
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.Module
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

val repositoriesModule = module {
    single<ToDoRepository> {
        get<Retrofit>().create(ToDoRepository::class.java)
    }
}

val reduxModule = module {
    single { AppReducer() }

    single<ReduxStore<AppState, AppAction>> {
        val store = Store(currentState = AppState.getInitialState(androidApplication()), reducer = { state, action: AppAction ->
            get<AppReducer>().reduce(action = action, state = state)
        })
        store.applyMiddleware(createEpicMiddleware(combineEpics(get<LoadToDoEpic>(), get<PatchToDoEpic>()), store), ActionLoggerMiddleware())
        store
    }
}


val epicsModule = module {
    single { LoadToDoEpic(get()) }
    single { PatchToDoEpic(get()) }
}

val networkModule = module {

    single<Retrofit> {
        Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .client(get())
                .addConverterFactory(GsonConverterFactory.create(get()))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
    }

    single<Gson> { GsonBuilder().create() }

    single { HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY } }

    single<OkHttpClient> {
        OkHttpClient.Builder().addInterceptor(get<HttpLoggingInterceptor>()).build()
    }
}

val appComponent: List<Module> = listOf(repositoriesModule, epicsModule, reduxModule, networkModule)


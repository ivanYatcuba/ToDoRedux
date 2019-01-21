package com.lanars.todoredux.flow.main

import com.lanars.todoredux.flow.main.model.ToDo
import io.reactivex.Single
import retrofit2.http.GET

interface ToDoRepository {

    @GET("todos")
    fun loadToDos(): Single<List<ToDo>>

}
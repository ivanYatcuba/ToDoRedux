package com.lanars.todoredux.flow.main

import com.lanars.todoredux.flow.main.model.ToDo
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.Path

interface ToDoRepository {

    @GET("todos")
    fun loadToDos(): Single<List<ToDo>>

    @PATCH("todos/{id}")
    fun patchToDo(@Path("id") id: Long, @Body todo: ToDo): Single<ToDo>

}
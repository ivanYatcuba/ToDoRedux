package com.lanars.todoredux.flow.main

import com.lanars.todoredux.flow.main.model.ToDo

class MainState {
    var todos: List<ToDo>? = listOf()
    var error: Throwable? = null
}
package com.lanars.todoredux.flow.main

import com.lanars.todoredux.flow.main.model.ToDo
import java.util.*

class MainState {
    var todos: MutableList<ToDo>? = LinkedList()
    var error: Throwable? = null
    var changedItemIndex = -1
}
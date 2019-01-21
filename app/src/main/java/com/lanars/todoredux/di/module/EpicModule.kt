package com.lanars.todoredux.di.module

import com.lanars.todoredux.flow.main.ToDoRepository
import com.lanars.todoredux.flow.main.epic.LoadToDoEpic
import com.lanars.todoredux.flow.main.epic.PatchToDoEpic
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Suppress("unused")
@Module
class EpicModule {

    @Provides
    @Singleton
    fun provideLoadToDoEpic(toDoRepository: ToDoRepository): LoadToDoEpic = LoadToDoEpic(toDoRepository)

    @Provides
    @Singleton
    fun providePatchToDoEpic(toDoRepository: ToDoRepository): PatchToDoEpic = PatchToDoEpic(toDoRepository)
}
package com.lanars.todoredux.di.module

import com.lanars.todoredux.flow.main.ToDoRepository
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton

@Suppress("unused")
@Module
class RepositoriesModule {

    @Provides
    @Singleton
    fun provideToDoRepository(retrofit: Retrofit): ToDoRepository = retrofit.create(ToDoRepository::class.java)
}
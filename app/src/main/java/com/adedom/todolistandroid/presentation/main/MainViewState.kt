package com.adedom.todolistandroid.presentation.main

import com.adedom.todolist.business.model.TodolistAll

data class MainViewState(
    val todolistAll: List<TodolistAll> = emptyList(),
    val isLoading: Boolean = false,
)

package com.adedom.todolistandroid.presentation.addtodolist

data class AddTodolistViewState(
    val title: String = "",
    val content: String = "",
    val isLoading: Boolean = false,
)

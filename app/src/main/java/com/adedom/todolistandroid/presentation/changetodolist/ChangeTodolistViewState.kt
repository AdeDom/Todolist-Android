package com.adedom.todolistandroid.presentation.changetodolist

data class ChangeTodolistViewState(
    val todolistId: String? = "",
    val title: String? = "",
    val content: String? = "",
    val isLoading: Boolean = false,
)

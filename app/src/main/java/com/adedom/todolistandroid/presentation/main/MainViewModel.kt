package com.adedom.todolistandroid.presentation.main

import com.adedom.todolist.models.response.BaseResponse
import com.adedom.todolistandroid.data.TodolistApi
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlin.coroutines.CoroutineContext

class MainViewModel : CoroutineScope {

    private val job = SupervisorJob()
    private val exceptionHandler = CoroutineExceptionHandler { _, err ->
        _error.value = err
    }

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main + exceptionHandler

    private val _error = MutableStateFlow(Throwable())
    val error: StateFlow<Throwable>
        get() = _error

    private val _state = MutableStateFlow(MainViewState())
    val state: StateFlow<MainViewState>
        get() = _state

    private val api by lazy { TodolistApi() }

    private var removeTodolistListener: ((BaseResponse) -> Unit)? = null

    fun callFetchTodolistAll() {
        launch {
            setState { copy(isLoading = true) }

            val response = api.callFetchTodolistAll()
            if (response.success) {
                setState { copy(todolistAll = response.todolistAll) }
            }

            setState { copy(isLoading = false) }
        }
    }

    fun callRemoveTodolist(todolistId: String?) {
        launch {
            setState { copy(isLoading = true) }

            val response = api.callRemoveTodolist(todolistId)
            removeTodolistListener?.invoke(response)

            setState { copy(isLoading = false) }
        }
    }

    fun removeTodolistListener(removeTodolistListener: (BaseResponse) -> Unit) {
        this.removeTodolistListener = removeTodolistListener
    }

    private fun setState(reducer: MainViewState.() -> MainViewState) {
        _state.value = _state.value.reducer()
    }

}

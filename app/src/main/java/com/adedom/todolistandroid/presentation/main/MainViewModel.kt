package com.adedom.todolistandroid.presentation.main

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

    private fun setState(reducer: MainViewState.() -> MainViewState) {
        _state.value = _state.value.reducer()
    }

}

package com.adedom.todolistandroid.presentation.addtodolist

import com.adedom.todolist.models.request.AddTodolistRequest
import com.adedom.todolist.models.response.BaseResponse
import com.adedom.todolistandroid.data.TodolistApi
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlin.coroutines.CoroutineContext

class AddTodolistViewModel : CoroutineScope {

    private val job = SupervisorJob()
    private val exceptionHandler = CoroutineExceptionHandler { _, err ->
        _error.value = err
    }

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main + exceptionHandler

    private val _error = MutableStateFlow(Throwable())
    val error: StateFlow<Throwable>
        get() = _error

    private val _state = MutableStateFlow(AddTodolistViewState())
    val state: StateFlow<AddTodolistViewState>
        get() = _state

    private val api by lazy { TodolistApi() }

    private var callAddTodolistListener: ((BaseResponse) -> Unit)? = null

    fun callAddTodolist() {
        launch {
            setState { copy(isLoading = true) }

            val request = AddTodolistRequest(
                todolistId = System.currentTimeMillis().toString(),
                title = state.value.title,
                content = state.value.content,
            )
            val response = api.callAddTodolist(request)
            callAddTodolistListener?.invoke(response)

            setState { copy(isLoading = false) }
        }
    }

    fun setStateTitle(title: String) {
        setState { copy(title = title) }
    }

    fun setStateContent(content: String) {
        setState { copy(content = content) }
    }

    fun setListenerCallAddTodolist(callAddTodolistListener: (BaseResponse) -> Unit) {
        this.callAddTodolistListener = callAddTodolistListener
    }

    private fun setState(reducer: AddTodolistViewState.() -> AddTodolistViewState) {
        _state.value = _state.value.reducer()
    }

}

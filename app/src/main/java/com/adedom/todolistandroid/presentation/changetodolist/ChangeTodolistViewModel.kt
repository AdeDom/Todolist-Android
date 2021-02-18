package com.adedom.todolistandroid.presentation.changetodolist

import com.adedom.todolist.models.request.ChangeTodolistRequest
import com.adedom.todolist.models.response.BaseResponse
import com.adedom.todolistandroid.data.TodolistApi
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlin.coroutines.CoroutineContext

class ChangeTodolistViewModel : CoroutineScope {

    private val job = SupervisorJob()
    private val exceptionHandler = CoroutineExceptionHandler { _, err ->
        _error.value = err
    }

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main + exceptionHandler

    private val _error = MutableStateFlow(Throwable())
    val error: StateFlow<Throwable>
        get() = _error

    private val _state = MutableStateFlow(ChangeTodolistViewState())
    val state: StateFlow<ChangeTodolistViewState>
        get() = _state

    private val api by lazy { TodolistApi() }

    private var callChangeTodolistListener: ((BaseResponse) -> Unit)? = null

    fun callChangeTodolist() {
        launch {
            setState { copy(isLoading = true) }

            val request = ChangeTodolistRequest(
                todolistId = state.value.todolistId,
                title = state.value.title,
                content = state.value.content,
            )
            val response = api.callChangeTodolist(request)
            callChangeTodolistListener?.invoke(response)

            setState { copy(isLoading = false) }
        }
    }

    fun setStateTodolistId(todolistId: String?) {
        setState { copy(todolistId = todolistId) }
    }

    fun setStateTitle(title: String?) {
        setState { copy(title = title) }
    }

    fun setStateContent(content: String?) {
        setState { copy(content = content) }
    }

    fun setListenerCallChangeTodolist(callChangeTodolistListener: (BaseResponse) -> Unit) {
        this.callChangeTodolistListener = callChangeTodolistListener
    }

    private fun setState(reducer: ChangeTodolistViewState.() -> ChangeTodolistViewState) {
        _state.value = _state.value.reducer()
    }

}

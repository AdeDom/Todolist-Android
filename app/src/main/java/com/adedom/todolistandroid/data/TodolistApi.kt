package com.adedom.todolistandroid.data

import com.adedom.todolist.models.response.TodolistAllResponse

interface TodolistApi {

    suspend fun callTodolistAll(): TodolistAllResponse

    companion object {
        const val BASE_URL = "https://todolist-server-94.herokuapp.com/"
    }

}

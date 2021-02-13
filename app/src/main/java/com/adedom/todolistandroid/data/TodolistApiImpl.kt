package com.adedom.todolistandroid.data

import com.adedom.todolist.models.response.TodolistAllResponse
import com.adedom.todolistandroid.data.TodolistApi.Companion.BASE_URL
import io.ktor.client.request.*

class TodolistApiImpl : TodolistApi {

    override suspend fun callTodolistAll(): TodolistAllResponse {
        return client.get("${BASE_URL}api/todolist/todolist-all")
    }

}

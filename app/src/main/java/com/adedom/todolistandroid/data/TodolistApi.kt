package com.adedom.todolistandroid.data

import com.adedom.todolist.models.request.AddTodolistRequest
import com.adedom.todolist.models.request.ChangeTodolistRequest
import com.adedom.todolist.models.response.BaseResponse
import com.adedom.todolist.models.response.TodolistAllResponse
import io.ktor.client.request.*
import io.ktor.http.*

class TodolistApi {

    suspend fun callFetchTodolistAll(): TodolistAllResponse {
        return client.get("${BASE_URL}api/todolist/todolist-all")
    }

    suspend fun callAddTodolist(addTodolistRequest: AddTodolistRequest): BaseResponse {
        return client.post("${BASE_URL}api/todolist/add-todolist") {
            contentType(ContentType.Application.Json)
            body = addTodolistRequest
        }
    }

    suspend fun callChangeTodolist(changeTodolistRequest: ChangeTodolistRequest): BaseResponse {
        return client.put("${BASE_URL}api/todolist/change-todolist") {
            contentType(ContentType.Application.Json)
            body = changeTodolistRequest
        }
    }

    suspend fun callRemoveTodolist(todolistId: String): BaseResponse {
        return client.delete("${BASE_URL}api/todolist/remove-todolist/$todolistId")
    }

    companion object {
        const val BASE_URL = "https://todolist-server-94.herokuapp.com/"
//        const val BASE_URL = "http://192.168.43.22:8080/"
    }

}

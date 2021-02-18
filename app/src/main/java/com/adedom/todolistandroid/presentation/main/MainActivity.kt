package com.adedom.todolistandroid.presentation.main

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.asLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.adedom.todolistandroid.R
import com.adedom.todolistandroid.presentation.addtodolist.AddTodolistActivity
import com.adedom.todolistandroid.presentation.changetodolist.ChangeTodolistActivity
import com.adedom.todolistandroid.presentation.model.TodolistAllParcelable
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val viewModel by lazy { MainViewModel() }
    private val mAdapter by lazy { MainAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initialView()
        observeViewModel()
        viewEvent()
    }

    override fun onStart() {
        super.onStart()

        viewModel.callFetchTodolistAll()
    }

    private fun initialView() {
        recyclerView.apply {
            layoutManager = LinearLayoutManager(baseContext)
            adapter = mAdapter
        }
    }

    private fun observeViewModel() {
        viewModel.state.asLiveData().observe(this, { state ->
            progressBar.isVisible = state.isLoading

            val todolistAll = state.todolistAll.map {
                TodolistAllParcelable(
                    todolistId = it.todolistId,
                    userId = it.userId,
                    title = it.title,
                    content = it.content,
                    createdLong = it.createdLong,
                    updatedLong = it.updatedLong,
                    createdString = it.createdString,
                    updatedString = it.updatedString,
                    isShow = it.isShow,
                )
            }
            mAdapter.submitList(todolistAll)
        })

        viewModel.error.asLiveData().observe(this, { throwable ->
            throwable.message?.let {
                Toast.makeText(baseContext, "${throwable.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun viewEvent() {
        mAdapter.setTodolistListener {
            Intent(baseContext, ChangeTodolistActivity::class.java).apply {
                putExtra("todolistAll", it)
                startActivity(this)
            }
        }

        fab.setOnClickListener {
            startActivity(Intent(baseContext, AddTodolistActivity::class.java))
        }
    }

}

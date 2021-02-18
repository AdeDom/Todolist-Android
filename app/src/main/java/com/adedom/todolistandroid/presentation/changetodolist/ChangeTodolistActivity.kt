package com.adedom.todolistandroid.presentation.changetodolist

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.asLiveData
import com.adedom.todolistandroid.R
import com.adedom.todolistandroid.presentation.model.TodolistAllParcelable
import kotlinx.android.synthetic.main.activity_change_todolist.*

class ChangeTodolistActivity : AppCompatActivity() {

    private val viewModel by lazy { ChangeTodolistViewModel() }
    private var mTodolistAll: TodolistAllParcelable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_todolist)

        mTodolistAll = intent.getParcelableExtra("todolistAll")

        initialView()
        viewModelObserve()
        viewEvent()
        listener()
    }

    private fun initialView() {
        viewModel.setStateTodolistId(mTodolistAll?.todolistId)
        viewModel.setStateTitle(mTodolistAll?.title)
        viewModel.setStateContent(mTodolistAll?.content)

        etTitle.setText(mTodolistAll?.title)
        etContent.setText(mTodolistAll?.content)
    }

    private fun viewModelObserve() {
        viewModel.state.asLiveData().observe(this, {
            progressBar.isVisible = it.isLoading
        })

        viewModel.error.asLiveData().observe(this, { throwable ->
            throwable.message?.let {
                Toast.makeText(baseContext, "${throwable.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun viewEvent() {
        viewModel.setListenerCallChangeTodolist {
            Toast.makeText(baseContext, "${it.message}", Toast.LENGTH_SHORT).show()
            if (it.success) {
                finish()
            }
        }
    }

    private fun listener() {
        etTitle.addTextChangedListener { viewModel.setStateTitle(it.toString()) }
        etContent.addTextChangedListener { viewModel.setStateContent(it.toString()) }

        btCancel.setOnClickListener {
            finish()
        }

        btOk.setOnClickListener {
            viewModel.callChangeTodolist()
        }
    }

}

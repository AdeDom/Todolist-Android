package com.adedom.todolistandroid.presentation.addtodolist

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.asLiveData
import com.adedom.todolistandroid.R
import kotlinx.android.synthetic.main.activity_add_todolist.*

class AddTodolistActivity : AppCompatActivity() {

    private val viewModel by lazy { AddTodolistViewModel() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_todolist)

        viewModelObserve()
        viewEvent()
        listener()
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

    private fun listener() {
        viewModel.setListenerCallAddTodolist {
            Toast.makeText(baseContext, "${it.message}", Toast.LENGTH_SHORT).show()
            if (it.success){
                finish()
            }
        }
    }

    private fun viewEvent() {
        etTitle.addTextChangedListener { viewModel.setStateTitle(it.toString()) }
        etContent.addTextChangedListener { viewModel.setStateContent(it.toString()) }

        btCancel.setOnClickListener {
            finish()
        }

        btOk.setOnClickListener {
            viewModel.callAddTodolist()
        }
    }

}

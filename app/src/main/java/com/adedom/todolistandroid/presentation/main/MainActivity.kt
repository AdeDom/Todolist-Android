package com.adedom.todolistandroid.presentation.main

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.asLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.adedom.todolistandroid.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val viewModel by lazy { MainViewModel() }
    private val mAdapter by lazy { MainAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel.callTodolistAll()

        initialView()
        observeViewModel()
    }

    private fun initialView() {
        recyclerView.apply {
            layoutManager = LinearLayoutManager(baseContext)
            adapter = mAdapter
        }
    }

    private fun observeViewModel() {
        viewModel.state.asLiveData().observe(this, {
            progressBar.isVisible = it.isLoading

            mAdapter.submitList(it.todolistAll)
        })

        viewModel.error.asLiveData().observe(this, { throwable ->
            throwable.message?.let {
                Toast.makeText(baseContext, "${throwable.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

}

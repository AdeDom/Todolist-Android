package com.adedom.todolistandroid.presentation.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.adedom.todolistandroid.presentation.model.TodolistAllParcelable

class MainAdapter : RecyclerView.Adapter<MainAdapter.MainViewHolder>() {

    private val asyncListDiffer =
        AsyncListDiffer(this, object : DiffUtil.ItemCallback<TodolistAllParcelable>() {
            override fun areItemsTheSame(
                oldItem: TodolistAllParcelable,
                newItem: TodolistAllParcelable
            ): Boolean {
                return oldItem.todolistId == newItem.todolistId
            }

            override fun areContentsTheSame(
                oldItem: TodolistAllParcelable,
                newItem: TodolistAllParcelable
            ): Boolean {
                return oldItem == newItem
            }
        })

    private val list: List<TodolistAllParcelable>
        get() = asyncListDiffer.currentList

    private var todolistListener: ((TodolistAllParcelable) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(android.R.layout.simple_list_item_2, parent, false)
        return MainViewHolder(view)
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.apply {
            val item = list[position]

            text1.text = item.title
            text2.text = item.content

            itemView.setOnClickListener {
                todolistListener?.invoke(item)
            }
        }
    }

    override fun getItemCount(): Int = list.size

    fun submitList(list: List<TodolistAllParcelable>) = asyncListDiffer.submitList(list)

    fun setTodolistListener(todolistListener: (TodolistAllParcelable) -> Unit) {
        this.todolistListener = todolistListener
    }

    inner class MainViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val text1: TextView = itemView.findViewById(android.R.id.text1)
        val text2: TextView = itemView.findViewById(android.R.id.text2)
    }

}

package com.developer.timurnav.todolist

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class TodoListAdapter(
        private val context: Context,
        private val list: ArrayList<TodoItem>
) : RecyclerView.Adapter<TodoListAdapter.TodoItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): TodoItemViewHolder {
        val view = LayoutInflater.from(context)
                .inflate(R.layout.todo_item_row, parent, false)
        return TodoItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: TodoItemViewHolder?, position: Int) {
        holder?.bindView(list[position])
    }

    override fun getItemCount() = list.size

    inner class TodoItemViewHolder(todoItemView: View) : RecyclerView.ViewHolder(todoItemView) {

        private val nameView = todoItemView.findViewById<TextView>(R.id.listChoreName)

        fun bindView(todoItem: TodoItem) {
            nameView.text = todoItem.name
        }

    }
}


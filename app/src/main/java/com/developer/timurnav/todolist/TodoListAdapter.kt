package com.developer.timurnav.todolist

import android.content.Intent
import android.support.v4.content.ContextCompat.getColor
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import java.text.DateFormat
import java.util.*

class TodoListAdapter(
        private val context: AppCompatActivity,
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

        private val nameView = todoItemView.findViewById<TextView>(R.id.todoItemName)
        private val descriptionView = todoItemView.findViewById<TextView>(R.id.todoItemDescription)
        private val dateView = todoItemView.findViewById<TextView>(R.id.todoItemDate)
        private val doneButton = todoItemView.findViewById<Button>(R.id.todoItemDoneButton)
        private val cardView = todoItemView.findViewById<CardView>(R.id.todoItemCard)

        init {
            todoItemView.findViewById<Button>(R.id.todoItemDeleteButton).setOnClickListener {
                list.removeAt(adapterPosition)
                notifyItemRemoved(adapterPosition)
            }
            todoItemView.findViewById<Button>(R.id.todoItemEditButton).setOnClickListener {
                val intent = Intent(context, EditTodoItemActivity::class.java)
                intent.putExtra(EDIT_ITEM, list[adapterPosition])
                intent.putExtra(ITEM_INDEX, adapterPosition)
                context.startActivityForResult(intent, REQUEST_CODE)
            }
            doneButton.setOnClickListener {
                list[adapterPosition].done = true
                notifyItemChanged(adapterPosition)
            }
        }


        fun bindView(todoItem: TodoItem) {
            nameView.text = todoItem.name
            descriptionView.text = todoItem.description
            dateView.text = DateFormat.getDateInstance().format(Date(todoItem.created))
            if (todoItem.done) {
                doneButton.visibility = View.GONE
                cardView.setBackgroundColor(getColor(context, R.color.colorAccent))
            } else {
                doneButton.visibility = View.VISIBLE
                cardView.setBackgroundColor(getColor(context, R.color.colorPrimary))
            }
        }

    }
}


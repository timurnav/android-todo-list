package com.developer.timurnav.todolist

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_edit_todo_item.*


class EditTodoItemActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_todo_item)

        val itemIndex = intent.getIntExtra(ITEM_INDEX, -1)
        intent.getParcelableExtra<TodoItem>(EDIT_ITEM)?.let {
            taskName.setText(it.name, TextView.BufferType.EDITABLE)
            taskDescription.setText(it.description, TextView.BufferType.EDITABLE)
        }

        saveButton.setOnClickListener {
            if (taskName.text.toString().isEmpty()) {
                Toast.makeText(this, "Enter Task Name", Toast.LENGTH_LONG).show()
            } else if (taskDescription.text.toString().isEmpty()) {
                Toast.makeText(this, "Enter Task Description", Toast.LENGTH_LONG).show()
            } else {
                val answerIntent = Intent()
                answerIntent.putExtra(ITEM_INDEX, itemIndex)
                answerIntent.putExtra(EDIT_ITEM, TodoItem(
                        name = taskName.text.toString(),
                        description = taskDescription.text.toString()
                ))
                setResult(RESULT_OK, answerIntent)
                finish()
            }

        }
    }
}

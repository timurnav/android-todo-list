package com.developer.timurnav.todolist

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_edit_todo_item.*


class EditTodoItemActivity : AppCompatActivity() {

    private val todoItemDAO = TodoItemDAO(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_todo_item)

        val editedItem = todoItemDAO.fetchOne(intent.getIntExtra(ITEM_ID, -1))
        editedItem?.let {
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
                answerIntent.putExtra(ITEM_NAME, taskName.text.toString())
                answerIntent.putExtra(ITEM_DESCRIPTION, taskDescription.text.toString())
                editedItem?.let { answerIntent.putExtra(ITEM_ID, it.id) }
                setResult(RESULT_OK, answerIntent)
                finish()
            }
        }
    }
}

package com.developer.timurnav.todolist

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    private val list = ArrayList<TodoItem>()
    private val todoItemDAO = TodoItemDAO(this)
    private val listAdapter = TodoListAdapter(this, list, todoItemDAO)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fab.setOnClickListener {
            val intent = Intent(this, EditTodoItemActivity::class.java)
            startActivityForResult(intent, REQUEST_CODE)
        }



        list.clear()
        list.addAll(todoItemDAO.fetchAll())

        todoList.layoutManager = LinearLayoutManager(this)
        todoList.adapter = listAdapter
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.top_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        if (item!!.itemId == R.id.button_more_vert) {
            Toast.makeText(this, "Drop menu", Toast.LENGTH_SHORT).show()
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode != REQUEST_CODE || resultCode != Activity.RESULT_OK) return
        data?.let {
            val id = it.getIntExtra(ITEM_ID, -1)
            val name = it.getStringExtra(ITEM_NAME)
            val description = it.getStringExtra(ITEM_DESCRIPTION)
            if (id == -1) {
                val newItem = todoItemDAO.create(name, description)
                list.add(newItem)
                listAdapter.notifyItemInserted(list.size - 1)
            } else {
                val item = TodoItem(id, name, description)
                val index = (0 until list.size)
                        .find { list[it].id == id }!!
                list[index] = item
                todoItemDAO.update(item)
                listAdapter.notifyItemChanged(index)
            }
        }
    }
}

package com.developer.timurnav.todolist

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val list = ArrayList<TodoItem>()
    private val listAdapter = TodoListAdapter(this, list)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        todoList.layoutManager = LinearLayoutManager(this)
        todoList.adapter = listAdapter
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.top_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        if (item!!.itemId == R.id.add_menu_button) {
            list.add(TodoItem("AAA"))
            listAdapter.notifyDataSetChanged()
        }

        return super.onOptionsItemSelected(item)
    }

}

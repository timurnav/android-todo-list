package com.developer.timurnav.todolist

import android.app.Activity
import android.content.Intent
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
            val intent = Intent(this, EditTodoItemActivity::class.java)
            startActivityForResult(intent, REQUEST_CODE)
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode != REQUEST_CODE || resultCode != Activity.RESULT_OK) return
        data?.let {
            val index = it.getIntExtra(ITEM_INDEX, -1)
            it.getParcelableExtra<TodoItem>(EDIT_ITEM)
                    ?.let { item ->
                        if (index > -1 && index < list.size) {
                            list[index] = item
                            listAdapter.notifyItemChanged(index)
                        } else {
                            list.add(item)
                            listAdapter.notifyDataSetChanged()
                        }
                    }
        }
    }
}

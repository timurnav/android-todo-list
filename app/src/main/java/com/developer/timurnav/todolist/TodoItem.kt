package com.developer.timurnav.todolist

data class TodoItem(
        val id: Int,
        var name: String,
        var description: String,
        var done: Boolean = false,
        var created: Long = System.currentTimeMillis()
)
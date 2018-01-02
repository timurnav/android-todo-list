package com.developer.timurnav.todolist

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class TodoItemDAO(context: Context)
    : SQLiteOpenHelper(context, "todos.db", null, 1) {

    private val TABLE_NAME = "TODOS"

    private val ID = "ID"
    private val NAME = "NAME"
    private val DESCRIPTION = "DESCRIPTION"
    private val CREATED_AT = "CREATED_AT"
    private val DONE = "DONE"

    override fun onCreate(db: SQLiteDatabase?) {
        val createTableQuery = """
            CREATE TABLE $TABLE_NAME (
                $ID INTEGER PRIMARY KEY,
                $NAME TEXT,
                $DESCRIPTION TEXT,
                $DONE INTEGER,
                $CREATED_AT LONG
            )
        """.trimIndent()
        db?.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME)
        onCreate(db)
    }

    fun create(name: String, description: String): TodoItem {
        val values = contentValues(name, description)

        val db = writableDatabase
        val id = db.insert(TABLE_NAME, null, values)
        db.close()
        return fetchOne(id.toInt())!!
    }

    fun update(item: TodoItem) {
        val (id, name, description, done) = item
        val values = contentValues(name, description, done)

        val db = writableDatabase
        db.update(TABLE_NAME, values, "$ID=?", arrayOf(id.toString()))
        db.close()
    }

    fun delete(id: Int) {
        val db = writableDatabase
        db.delete(TABLE_NAME, "$ID=?", arrayOf(id.toString()))
        db.close()
    }

    fun fetchAll(): ArrayList<TodoItem> {
        val list = ArrayList<TodoItem>()

        val cursor: Cursor = readableDatabase.rawQuery(
                "SELECT * FROM $TABLE_NAME",
                null)
        if (cursor.moveToFirst()) {
            do {
                list.add(mapOnItem(cursor))

            } while (cursor.moveToNext())
        }
        cursor.close()
        return list
    }

    fun fetchOne(id: Int): TodoItem? {
        val cursor: Cursor = readableDatabase.rawQuery(
                "SELECT * FROM $TABLE_NAME WHERE $ID=$id",
                null)
        val result = if (cursor.moveToFirst()) mapOnItem(cursor) else null
        cursor.close()
        return result
    }

    private fun contentValues(
            name: String,
            description: String,
            done: Boolean = false
    ): ContentValues {
        val values = ContentValues()
        values.put(NAME, name)
        values.put(DESCRIPTION, description)
        values.put(DONE, if (done) 1 else 0)
        values.put(CREATED_AT, System.currentTimeMillis())
        return values
    }

    private fun mapOnItem(cursor: Cursor): TodoItem {
        return TodoItem(
                id = cursor.getInt(cursor.getColumnIndex(ID)),
                name = cursor.getString(cursor.getColumnIndex(NAME)),
                description = cursor.getString(cursor.getColumnIndex(DESCRIPTION)),
                done = cursor.getInt(cursor.getColumnIndex(DONE)) == 1,
                created = cursor.getLong(cursor.getColumnIndex(CREATED_AT))
        )
    }
}
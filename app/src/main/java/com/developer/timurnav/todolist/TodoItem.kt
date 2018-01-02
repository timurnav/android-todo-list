package com.developer.timurnav.todolist

import android.os.Parcel
import android.os.Parcelable

data class TodoItem(
        var name: String,
        var description: String
) : Parcelable {
    var done = false
    val created = System.currentTimeMillis()

    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString()) {
        done = parcel.readByte() != 0.toByte()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(description)
        parcel.writeByte(if (done) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<TodoItem> {
        override fun createFromParcel(parcel: Parcel): TodoItem {
            return TodoItem(parcel)
        }

        override fun newArray(size: Int): Array<TodoItem?> {
            return arrayOfNulls(size)
        }
    }
}
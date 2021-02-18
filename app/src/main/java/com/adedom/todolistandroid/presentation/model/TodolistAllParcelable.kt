package com.adedom.todolistandroid.presentation.model

import android.os.Parcel
import android.os.Parcelable

data class TodolistAllParcelable(
    val todolistId: String?,
    val userId: String?,
    val title: String?,
    val content: String?,
    val createdLong: Long,
    val updatedLong: Long?,
    val createdString: String?,
    val updatedString: String?,
    val isShow: Boolean,
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readLong(),
        parcel.readValue(Long::class.java.classLoader) as? Long,
        parcel.readString(),
        parcel.readString(),
        parcel.readByte() != 0.toByte()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(todolistId)
        parcel.writeString(userId)
        parcel.writeString(title)
        parcel.writeString(content)
        parcel.writeLong(createdLong)
        parcel.writeValue(updatedLong)
        parcel.writeString(createdString)
        parcel.writeString(updatedString)
        parcel.writeByte(if (isShow) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<TodolistAllParcelable> {
        override fun createFromParcel(parcel: Parcel): TodolistAllParcelable {
            return TodolistAllParcelable(parcel)
        }

        override fun newArray(size: Int): Array<TodolistAllParcelable?> {
            return arrayOfNulls(size)
        }
    }
}

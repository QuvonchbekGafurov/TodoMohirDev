package com.example.todomohirdev.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.versionedparcelable.ParcelField
import androidx.versionedparcelable.ParcelUtils
import androidx.versionedparcelable.VersionedParcelize

@Entity(tableName = "todo_table")
data class Todo(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val date: String,
    val name: String,
    val title: String,
    val hour: String,
    val checked: Boolean
)
package com.example.taskappnavarro.model.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "data_entity")
class TaskEntity(
    //CreationDate y DueDate pudieron ser de tipo Date
    @PrimaryKey val id: String,
    @ColumnInfo(name = "title") val title: String,
    @ColumnInfo(name = "content") val content: String,
    @ColumnInfo(name = "creationDate") val creationDate: String,
    @ColumnInfo(name = "dueDate") val dueDate: String,
    @ColumnInfo(name = "image") val image: String,
    @ColumnInfo(name = "isDone") val completed: Boolean
)
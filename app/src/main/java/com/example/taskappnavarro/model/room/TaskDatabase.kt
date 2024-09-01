package com.example.taskappnavarro.model.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [TaskEntity::class], version = 2)
abstract class TaskDatabase : RoomDatabase() {
    abstract fun dataDao(): TaskDao
}
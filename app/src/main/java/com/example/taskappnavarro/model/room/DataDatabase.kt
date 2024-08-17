package com.example.taskappnavarro.model.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [DataEntity::class], version = 2)
abstract class DataDatabase : RoomDatabase() {
    abstract fun dataDao(): DataDao
}
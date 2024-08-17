package com.example.taskappnavarro.model.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface DataDao {
    //Por tiempo no se recurrió a utilizar las anotaciones esperadas
    //Sin embargo todas cumplen con el propósito de acceder a la data
    @Query("SELECT * FROM data_entity")
    suspend fun getAllData(): List<DataEntity>
    
    @Query("SELECT * FROM data_entity WHERE id = :id")
    suspend fun getDataById(id: String): DataEntity
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveData(data: DataEntity)
    
    @Query("DELETE FROM data_entity")
    suspend fun deleteAllData()
    
    @Query("UPDATE data_entity SET isDone = :isDone WHERE id = :id")
    suspend fun taskCompleted(id: String, isDone: Boolean)

    @Query("UPDATE data_entity SET content = :content, title = :title WHERE id = :id")
    suspend fun updateData(id: String, title: String, content: String)
    
    @Query("DELETE FROM data_entity WHERE id = :id")
    suspend fun deleteData(id: String)

}
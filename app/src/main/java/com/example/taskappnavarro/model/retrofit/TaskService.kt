package com.example.taskappnavarro.model.retrofit

import com.example.taskappnavarro.TaskManagement
import com.example.taskappnavarro.model.room.TaskEntity
import com.example.taskappnavarro.model.toDataEntity
import com.example.taskappnavarro.model.model.Task
import com.example.taskappnavarro.model.provider.TaskProvider
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TaskService {
    private val taskProvider = TaskProvider()

    //El siguiente método cumple con la responsabilidad de vincular la información
    //de la API con la de la base de datos.
    suspend fun getData(): List<TaskEntity> {
        val dataFromDatabase = getDataFromDatabase()

        if (dataFromDatabase.isEmpty()) {
            val dataFromApi = getDataFromAPI()
            saveAllDataOnDatabase(dataFromApi)
            return withContext(Dispatchers.IO) {
                getDataFromDatabase()
            }
        }
        return withContext(Dispatchers.IO) {
            getDataFromDatabase()
        }
    }
    
    //Debido a que sólo se cumple con acceder a la data con la API
    //los demás métodos sólo se encargarán de acceder a la base de datos.
    suspend fun getDataById(id: String): TaskEntity {
        return withContext(Dispatchers.IO) {
            TaskManagement.database.dataDao().getDataById(id)
        }
    }

    suspend fun getDataFromDatabase(): List<TaskEntity> =
        TaskManagement.database.dataDao().getAllData()

    suspend fun getDataFromAPI(): List<Task> {
        val response = taskProvider.provideRetrofit().create(TaskApiClient::class.java).getData()
        if (response.isSuccessful) {
            val data = response.body()
            return data?.data ?: emptyList()
        } else {
            return emptyList()
        }
    }
    
    suspend fun saveAllDataOnDatabase(data: List<Task>) {
        data.forEach { dataModel ->
            val dataEntity = dataModel.toDataEntity()
            saveData(dataEntity)
        }
    }
    
    suspend fun saveData(data: TaskEntity) {
        TaskManagement.database.dataDao().saveData(data)
    }
    
    suspend fun saveCompletedTask(id: String, isCompleted: Boolean){
        TaskManagement.database.dataDao().taskCompleted(id, isCompleted)
    }
    
    suspend fun updateData(id: String, title: String, content: String) {
        TaskManagement.database.dataDao().updateData(id, title, content)
    }

    suspend fun deleteData(id: String) {
        TaskManagement.database.dataDao().deleteData(id)

    }
}
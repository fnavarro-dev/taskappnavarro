package com.example.taskappnavarro.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskappnavarro.model.room.DataEntity
import com.example.taskappnavarro.model.retrofit.TaskService
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.util.UUID

class DataViewModel : ViewModel() {
    private val taskService = TaskService()
    private var dataListFromDatabase: List<DataEntity> = emptyList()
    val data = MutableLiveData<List<DataEntity>>(null)
    val currentData = MutableLiveData<DataEntity>(null)

    fun getData() {
        viewModelScope.launch {
            val result = taskService.getData()
            dataListFromDatabase = result

            if (result.isNotEmpty()) {
                data.postValue(result)
            }
        }
    }

    fun getDataById(id: String) {
        viewModelScope.launch {
            val result = taskService.getDataById(id)
            setCurrentData(result)
        }
    }

    fun resultDataList() {
        data.postValue(dataListFromDatabase)
    }

    fun isTaskCompleted(id: String, isCompleted: Boolean) {
        viewModelScope.launch {
            taskService.saveCompletedTask(id, isCompleted)
            val result = taskService.getData()
            data.postValue(result)
        }
    }

    fun updateData(id: String, title: String, content: String) {
        viewModelScope.launch {
            taskService.updateData(id, title, content)
            val result = taskService.getData()
            data.postValue(result)
        }
    }

    //Se genera un UUID aleatorio para el id de la nueva tarea,
    //también se genera una fecha de creación y una fecha de vencimiento
    //para la tarea.
    fun createTask(title: String, content: String) {
        viewModelScope.launch {
            val uuid = UUID.randomUUID().toString()
            val currentDate = LocalDate.now()
            val creationDate = currentDate.toString()
            val dueDate = currentDate.plusYears(1).toString()
            val image = ""
            val isDone = false

            val newData = DataEntity(uuid, title, content, creationDate, dueDate, image, isDone)
            taskService.saveData(newData)
            val result = taskService.getData()
            data.postValue(result)
        }
    }

    private fun setCurrentData(item: DataEntity) {
        currentData.postValue(item)
    }

    fun deleteTask(id: String) {
        viewModelScope.launch {
            taskService.deleteData(id)
            val result = taskService.getData()
            data.postValue(result)
        }
    }

    //borrar temporalmente una tarea
    fun deleteTaskTemporarily(id: String) {
        viewModelScope.launch {
            // Elimina la tarea temporalmente de la lista
            val updatedList = dataListFromDatabase.filter { it.id != id }
            dataListFromDatabase = updatedList
            data.postValue(updatedList)
        }
    }

    // Recupera la tarea eliminada de la base de datos y la vuelve a añadir a la lista
    fun restoreTask(id: String) {
        viewModelScope.launch {
            // Recupera la tarea eliminada de la base de datos y la vuelve a añadir a la lista
            val restoredTask = taskService.getDataById(id)
            val updatedList = dataListFromDatabase.toMutableList().apply {
                add(restoredTask)
            }
            dataListFromDatabase = updatedList
            data.postValue(updatedList)
        }
    }


}
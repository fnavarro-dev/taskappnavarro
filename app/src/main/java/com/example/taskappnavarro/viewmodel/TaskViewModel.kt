package com.example.taskappnavarro.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.taskappnavarro.model.room.TaskEntity
import com.example.taskappnavarro.model.retrofit.TaskService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.util.UUID

class TaskViewModel : ViewModel() {
    private val taskService = TaskService()
    private var dataListFromDatabase: List<TaskEntity> = emptyList()
    val data = MutableLiveData<List<TaskEntity>>(null)
    val currentData = MutableLiveData<TaskEntity>(null)

    fun getData() {
        viewModelScope.launch(Dispatchers.IO) {
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


    fun createTask(title: String, content: String) {
        viewModelScope.launch {
            val uuid = UUID.randomUUID().toString()
            val currentDate = LocalDate.now()
            val creationDate = currentDate.toString()
            val dueDate = currentDate.plusYears(1).toString()
            val image = ""
            val isDone = false

            val newData = TaskEntity(uuid, title, content, creationDate, dueDate, image, isDone)
            taskService.saveData(newData)
            val result = taskService.getData()
            data.postValue(result)
        }
    }

    private fun setCurrentData(item: TaskEntity) {
        currentData.postValue(item)
    }

    fun deleteTask(id: String) {
        viewModelScope.launch {
            taskService.deleteData(id)
            val result = taskService.getData()
            data.postValue(result)
        }
    }




}
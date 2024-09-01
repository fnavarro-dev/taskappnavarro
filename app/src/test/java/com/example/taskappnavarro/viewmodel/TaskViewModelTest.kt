package com.example.taskappnavarro.viewmodel

import org.junit.Before
import org.junit.Test

//Se plantea un pie inicial para el testing, sin embargo no se cuenta con el dominio
//suficiente para poder realizar el testing utilizando las corrutinas.
class TaskViewModelTest {
    
    lateinit var taskViewModel: TaskViewModel
    
    @Before
    fun setUp() {
        taskViewModel = TaskViewModel()
    }

    @Test
    fun getDataById() {
        val id = "testId"
        taskViewModel.getDataById(id)
        val result = taskViewModel.currentData.value
        assert(result?.id == id)
    }

    @Test
    fun isTaskCompleted() {
        val id = "testId"
        val isCompleted = true
        taskViewModel.isTaskCompleted(id, isCompleted)
        val result = taskViewModel.data.value
        assert(result?.firstOrNull { it.id == id }?.completed == isCompleted)
    }

    @Test
    fun updateData() {
    }

    @Test
    fun createTask() {
        val title = "Test Title"
        val content = "Test Content"
        taskViewModel.createTask(title, content)
        val result = taskViewModel.data.value
        assert(result?.isNotEmpty() == true)
    }

    @Test
    fun deleteTask() {
        val id = "testId"
        taskViewModel.deleteTask(id)
        val result = taskViewModel.data.value
        assert(result?.none { it.id == id } == true)
    }
}
package com.example.taskappnavarro.viewmodel

import com.example.taskappnavarro.viewmodel.DataViewModel
import org.junit.Before
import org.junit.Test

//Se plantea un pie inicial para el testing, sin embargo no se cuenta con el dominio
//suficiente para poder realizar el testing utilizando las corrutinas.
class DataViewModelTest {
    
    lateinit var dataViewModel: DataViewModel
    
    @Before
    fun setUp() {
        dataViewModel = DataViewModel()
    }

    @Test
    fun getDataById() {
        val id = "testId"
        dataViewModel.getDataById(id)
        val result = dataViewModel.currentData.value
        assert(result?.id == id)
    }

    @Test
    fun isTaskCompleted() {
        val id = "testId"
        val isCompleted = true
        dataViewModel.isTaskCompleted(id, isCompleted)
        val result = dataViewModel.data.value
        assert(result?.firstOrNull { it.id == id }?.completed == isCompleted)
    }

    @Test
    fun updateData() {
    }

    @Test
    fun createTask() {
        val title = "Test Title"
        val content = "Test Content"
        dataViewModel.createTask(title, content)
        val result = dataViewModel.data.value
        assert(result?.isNotEmpty() == true)
    }

    @Test
    fun deleteTask() {
        val id = "testId"
        dataViewModel.deleteTask(id)
        val result = dataViewModel.data.value
        assert(result?.none { it.id == id } == true)
    }
}
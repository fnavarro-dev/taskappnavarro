package com.example.taskappnavarro

import android.app.Application
import com.example.taskappnavarro.model.room.TaskDatabase
import com.example.taskappnavarro.model.provider.TaskProvider

//Según sugerencias de algunos compañeros, se crea una clase llamada TaskManagement
//que hereda de Application. Esta clase se utiliza para inicializar la base de datos
//al iniciar la aplicación.
class TaskManagement : Application() {
    private val taskProvider = TaskProvider()
    
    companion object {
        lateinit var database: TaskDatabase
    }
    
    override fun onCreate() {
        super.onCreate()
        database = taskProvider.provideRoom(this)
    }
}
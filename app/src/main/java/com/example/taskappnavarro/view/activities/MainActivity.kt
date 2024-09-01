package com.example.taskappnavarro.view.activities

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.taskappnavarro.R
import com.example.taskappnavarro.databinding.ActivityMainBinding
import com.example.taskappnavarro.view.fragments.TaskListFragment
import com.example.taskappnavarro.viewmodel.TaskViewModel

class MainActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityMainBinding
    private val taskListFragment = TaskListFragment()
    
    private val taskViewModel: TaskViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configurar la barra de herramientas
        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = "TaskAppNavarro"



        initUI()
    }
    

    private fun initUI() {
        initUIState()
        initUIListener()
    }
    
    private fun initUIState() {
        taskViewModel.getData()
        startDataListFragment()
    }
    
    private fun startDataListFragment() {
        setCurrentFragment(taskListFragment)
        taskViewModel.resultDataList()
    }
    
    private fun initUIListener() {
        setCurrentFragment(taskListFragment)
    }
    
    private fun setCurrentFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.frameLayoutFragment, fragment)
            commit()
        }
    }

    // Método para actualizar la barra de herramientas cuando se muestra el detalle de la tarea
    fun updateToolbarForDetails(taskTitle: String) {
        supportActionBar?.apply {
            title = taskTitle
            setDisplayHomeAsUpEnabled(true)
        }
    }

    // Método para restaurar la barra de herramientas cuando se vuelve a la lista de tareas
    fun updateToolbarForList() {
        supportActionBar?.apply {
            title = "TaskAppNavarro"
            setDisplayHomeAsUpEnabled(false)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
package com.example.taskappnavarro.view.activities

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.taskappnavarro.R
import com.example.taskappnavarro.databinding.ActivityMainBinding
import com.example.taskappnavarro.view.fragments.TaskListFragment
import com.example.taskappnavarro.viewmodel.DataViewModel

class MainActivity : AppCompatActivity() {
    
    private lateinit var binding: ActivityMainBinding
    private val taskListFragment = TaskListFragment()
    
    private val dataViewModel: DataViewModel by viewModels()
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /*enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }*/
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initUI()
    }
    
    //Esta estructura se toma de la experiencia compartida por Pedro
    //La cual consiste en separar los métodos en diferentes responsabilidades
    private fun initUI() {
        initUIState()
        initUIListener()
    }
    
    private fun initUIState() {
        dataViewModel.getData()
        startDataListFragment()
    }
    
    private fun startDataListFragment() {
        setCurrentFragment(taskListFragment)
        dataViewModel.resultDataList()
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
}
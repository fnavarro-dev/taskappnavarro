package com.example.taskappnavarro.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.taskappnavarro.R
import com.example.taskappnavarro.databinding.FragmentNewTaskBinding
import com.example.taskappnavarro.viewmodel.DataViewModel

class NewTaskFragment : Fragment() {
    private lateinit var binding: FragmentNewTaskBinding
    private val taskViewModel: DataViewModel by activityViewModels()

    private fun setCurrentFragment(fragment: Fragment) {
        requireActivity().supportFragmentManager.beginTransaction().apply {
            replace(R.id.frameLayoutFragment, fragment)
            commit()
        }
    }
    
    private fun createTask() {
        binding.btnCrear.setOnClickListener {
            val title = binding.taskTitle.getText().toString()
            val content = binding.taskDescription.getText().toString()
            taskViewModel.createTask(title, content)
            setCurrentFragment(TaskListFragment())
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNewTaskBinding.inflate(inflater, container, false)
        createTask()
        
        //Al igual que en la vista detalles se incorpora la funcionalidad para regresar
        //a la lista de tareas.
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            setCurrentFragment(TaskListFragment())
        }
        return binding.root
    }
}
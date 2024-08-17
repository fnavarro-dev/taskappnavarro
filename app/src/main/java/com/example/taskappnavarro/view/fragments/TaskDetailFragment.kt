package com.example.taskappnavarro.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.taskappnavarro.R
import com.example.taskappnavarro.databinding.FragmentTaskDetailBinding
import com.example.taskappnavarro.viewmodel.DataViewModel
import com.squareup.picasso.Picasso

class TaskDetailFragment : Fragment() {
    private var _binding: FragmentTaskDetailBinding? = null
    private val binding get() = _binding!!
    private val taskViewModel: DataViewModel by activityViewModels()
    private val taskListFragment = TaskListFragment()
    private lateinit var taskID: String
    
    private fun getTaskData() {
        taskViewModel.getDataById(taskID)
        taskViewModel.currentData.observe(viewLifecycleOwner) {
            if (it != null) {
                binding.taskTitle.setText(it.title)
                binding.taskDescription.setText(it.content)
                
                //Se evalúa si existe algún enlace para colocar una imagen
                if (it.image.isNotEmpty()) {
                    Picasso.get().load(it.image).into(binding.imgData)
                }
            }
        }
    }
    
    private fun setCurrentFragment(fragment: Fragment) {
        requireActivity().supportFragmentManager.beginTransaction().apply {
            replace(R.id.frameLayoutFragment, fragment)
            commit()
        }
    }
    
    //Aquí se referencia al ViewModel para guardar una tarea nueva
    private fun completeButton() {
        binding.btnComplete.setOnClickListener {
            val title = binding.taskTitle.getText().toString()
            val content = binding.taskDescription.getText().toString()
            taskViewModel.updateData(taskID, title, content)
            setCurrentFragment(taskListFragment)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        taskID = arguments?.getString("taskID")!!
        _binding = FragmentTaskDetailBinding.inflate(inflater, container, false)
        getTaskData()
        completeButton()
        
        //Se incorpora la funcionalidad para volver a la vista principal
        //utilizando el botón de back.
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            setCurrentFragment(TaskListFragment())
        }
        return binding.root
    }
}
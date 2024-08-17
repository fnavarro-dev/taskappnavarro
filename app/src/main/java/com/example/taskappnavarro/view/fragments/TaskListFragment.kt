package com.example.taskappnavarro.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.taskappnavarro.model.toListDataModel
import com.example.taskappnavarro.R
import com.example.taskappnavarro.databinding.FragmentTaskListBinding
import com.example.taskappnavarro.view.adapters.TasksAdapter
import com.example.taskappnavarro.viewmodel.DataViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class TaskListFragment : Fragment() {
    private lateinit var binding: FragmentTaskListBinding
    private var taskAdapter: TasksAdapter = TasksAdapter()
    private val dataViewModel: DataViewModel by activityViewModels()
    private lateinit var recyclerView: RecyclerView

    private fun getTasks() {
        dataViewModel.data.observe(viewLifecycleOwner) {
            if(it != null && it.isNotEmpty()) {
                taskAdapter.setListTasks(it.toListDataModel())
            }
        }
    }

    private fun setCurrentFragment(fragment: Fragment) {
        requireActivity().supportFragmentManager.beginTransaction().apply {
            replace(R.id.frameLayoutFragment, fragment)
            commit()
        }
    }

    private fun setupTaskDetailNavigation() {
        taskAdapter.onClick = { taskID ->
            val bundle = bundleOf("taskID" to taskID)
            val taskDetailFragment = TaskDetailFragment()
            taskDetailFragment.arguments = bundle
            setCurrentFragment(taskDetailFragment)
        }
        // Se incorpora un listener para la casilla que se marca para indicar una tarea terminada
        taskAdapter.onCheckedChange = { taskID, isChecked ->
            dataViewModel.isTaskCompleted(taskID.id, isChecked)
        }
        // Se incorpora un listener para mostrar un diálogo de confirmación al mantener pulsada la tarea
        taskAdapter.onLongClick = { taskID ->
            mostrarDialogoConfirmacionEliminacion(taskID)
        }
    }

    private fun mostrarDialogoConfirmacionEliminacion(taskID: String) {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("¿Desea eliminar la tarea?")
            .setPositiveButton("Borrar") { _, _ ->
                dataViewModel.deleteTask(taskID)
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }


    private fun startRecyclerView() {
        recyclerView = binding.listView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = taskAdapter
    }

    private fun setupStartNewTask() {
        binding.btnNewTask.setOnClickListener {
            setCurrentFragment(NewTaskFragment())
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTaskListBinding.inflate(inflater, container, false)
        getTasks()
        startRecyclerView()
        setupTaskDetailNavigation()
        setupStartNewTask()
        return binding.root
    }
}
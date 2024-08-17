package com.example.taskappnavarro.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
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
import com.google.android.material.snackbar.Snackbar

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
        // Se incorpora un listener para eliminar una tarea con pulsación prolongada
        taskAdapter.onLongClick = { taskID ->
            showDeleteConfirmationDialog(taskID)
        }
    }

    private fun showDeleteConfirmationDialog(taskID: String) {
        val position = taskAdapter.tasks.indexOfFirst { it.id == taskID }
        val taskViewHolder = recyclerView.findViewHolderForAdapterPosition(position)

        AlertDialog.Builder(requireContext())
            .setTitle("¿Desea eliminar la tarea?")
            .setMessage("Esta acción no se puede deshacer.")
            .setPositiveButton("Borrar") { _, _ ->
                // Ocultar la tarea temporalmente
                taskViewHolder?.itemView?.visibility = View.GONE
                Snackbar.make(binding.root, "Tarea eliminada", Snackbar.LENGTH_LONG)
                    .setAction("Deshacer") {
                        // Mostrar la tarea nuevamente si se deshace
                        taskViewHolder?.itemView?.visibility = View.VISIBLE
                    }
                    .setDuration(5000) // 5 segundos para deshacer
                    .show()
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

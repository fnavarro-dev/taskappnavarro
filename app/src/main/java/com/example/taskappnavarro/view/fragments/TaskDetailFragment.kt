package com.example.taskappnavarro.view.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.taskappnavarro.R
import com.example.taskappnavarro.databinding.FragmentTaskDetailBinding
import com.example.taskappnavarro.view.activities.MainActivity
import com.example.taskappnavarro.viewmodel.TaskViewModel
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import java.lang.Exception

class TaskDetailFragment : Fragment() {
    private var _binding: FragmentTaskDetailBinding? = null
    private val binding get() = _binding!!
    private val taskViewModel: TaskViewModel by activityViewModels()
    private lateinit var taskID: String

    @SuppressLint("StringFormatInvalid")
    private fun getTaskData() {
        taskViewModel.getDataById(taskID)
        taskViewModel.currentData.observe(viewLifecycleOwner) {
            if (it != null) {
                binding.taskTitle.setText(it.title)
                binding.taskDescription.setText(it.content)

                // Actualizar la barra de herramientas con el título de la tarea
                (activity as MainActivity).updateToolbarForDetails(it.title)

                // Lógica para cargar la imagen o mostrar una por defecto
                if (it.image.isNotEmpty()) {
                    Picasso.get()
                        .load(it.image)
                        .error(R.drawable.ic_no_image) // Imagen por defecto en caso de error
                        .into(binding.imgData, object : Callback {
                            override fun onSuccess() {
                                // Si la imagen carga correctamente, no hacemos nada
                            }

                            override fun onError(e: Exception?) {
                                // En caso de error, mostramos la imagen por defecto
                                binding.imgData.setImageResource(R.drawable.ic_no_image)
                            }
                        })
                } else {
                    binding.imgData.setImageResource(R.drawable.ic_no_image)
                }

                // Mostrar las fechas
                binding.creationDate.text = getString(R.string.creation_date, it.creationDate)
                binding.dueDate.text = getString(R.string.due_date, it.dueDate)
            }
        }
    }

    private fun setCurrentFragment(fragment: Fragment) {
        requireActivity().supportFragmentManager.beginTransaction().apply {
            replace(R.id.frameLayoutFragment, fragment)
            commit()
        }
    }

    private fun completeButton() {
        binding.btnComplete.setOnClickListener {
            val title = binding.taskTitle.text.toString()
            val content = binding.taskDescription.text.toString()
            taskViewModel.updateData(taskID, title, content)
            setCurrentFragment(TaskListFragment())
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        taskID = arguments?.getString("taskID")!!
        _binding = FragmentTaskDetailBinding.inflate(inflater, container, false)

        // Obtener los datos de la tarea
        getTaskData()

        // Actualizar la barra de herramientas con el título de la tarea
        val taskTitle = binding.taskTitle.text.toString()
        (activity as MainActivity).updateToolbarForDetails(taskTitle)

        completeButton()

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            setCurrentFragment(TaskListFragment())
            (activity as MainActivity).updateToolbarForList()
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

package com.example.taskappnavarro.view.adapters

import android.graphics.Paint

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.taskappnavarro.R
import com.example.taskappnavarro.model.model.Task
import com.example.taskappnavarro.databinding.ItemTaskBinding
import com.squareup.picasso.Picasso
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException

class TasksAdapter(var tasks: List<Task> = emptyList()) :
    RecyclerView.Adapter<TasksAdapter.ViewHolder>() {

        //uso de callbacks para evitar el uso de interfaces
    var onClick: ((String) -> Unit)? = null
    var onCheckedChange: ((Task, Boolean) -> Unit)? = null
    var onLongClick: ((String) -> Unit)? = null
    
    fun setListTasks(tasks: List<Task>) {
        this.tasks = tasks
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: ItemTaskBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private val clienteHttp = OkHttpClient() // OkHttpClient en español

        fun onBind(task: Task) {
            binding.taskTitle.text = task.title
            binding.taskDescription.text = task.content

            if (task.image.isNotEmpty()) {
                verificarYCargarImagen(task.image) // Nombre de función en español
            } else {
                binding.imgData.visibility = View.GONE
            }

            if(task.isDone) {
                binding.checkbox.isChecked = true
                binding.taskTitle.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
                binding.taskDescription.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
            } else {
                binding.checkbox.isChecked = false
                binding.taskTitle.paintFlags = 0
                binding.taskDescription.paintFlags = 0
            }

            val taskID = task.id
            binding.itemTask.setOnClickListener {
                onClick?.invoke(taskID)
            }
            binding.checkbox.setOnCheckedChangeListener { _, isChecked ->
                onCheckedChange?.invoke(task, isChecked)
            }
            binding.itemTask.setOnLongClickListener {
                onLongClick?.invoke(task.id)
                true
            }
        }

       //añadimos esta función porque la api tiene las url de imagen caídas, error 404 not found
        private fun verificarYCargarImagen(urlImagen: String) {
            // Realiza la solicitud de verificación en un hilo separado
            Thread {
                try {
                    val solicitud = Request.Builder().url(urlImagen).build() // 'request' en español
                    val respuesta = clienteHttp.newCall(solicitud).execute() // 'response' en español
                    if (respuesta.isSuccessful) {
                        // URL válida, carga la imagen en el ImageView
                        itemView.post {
                            binding.imgData.visibility = View.VISIBLE
                            Picasso.get().load(urlImagen).into(binding.imgData)
                        }
                    } else {
                        // URL inválida, muestra imagen por defecto
                        itemView.post {
                            binding.imgData.visibility = View.VISIBLE
                            binding.imgData.setImageResource(R.drawable.ic_no_image) // Imagen por defecto
                        }
                    }
                } catch (e: IOException) {
                    // Error en la conexión o URL inválida, muestra imagen por defecto
                    itemView.post {
                        binding.imgData.visibility = View.VISIBLE
                        binding.imgData.setImageResource(R.drawable.ic_no_image) // Imagen por defecto
                    }
                }
            }.start()
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemTaskBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun getItemCount(): Int = tasks.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(tasks[position])
    }
    
    //Para que funcione bien el RecyclerView
    override fun getItemViewType(position: Int) = position
}
package com.example.taskappnavarro.view.fragments

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.taskappnavarro.R
import com.example.taskappnavarro.databinding.FragmentStartBinding

class StartFragment : Fragment() {
    private lateinit var binding: FragmentStartBinding
    private val taskListFragment = TaskListFragment()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = FragmentStartBinding.inflate(inflater, container, false)



        // Retraso de 3 segundos antes de navegar a la pantalla principal
        Handler(Looper.getMainLooper()).postDelayed({
            setCurrentFragment(taskListFragment)
        }, 3000) // 3000 ms = 3 segundos

        return binding.root
    }

    private fun setCurrentFragment(fragment: Fragment) {
        requireActivity().supportFragmentManager.beginTransaction().apply {
            replace(R.id.frameLayout, fragment)
            commit()
        }
    }
}

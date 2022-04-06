package com.example.tusk.presentation.feature.all_tasks

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.tusk.R

class AllTasksFragment: Fragment() {
    private val viewModel: AllTasksViewModel by lazy {
        ViewModelProvider(this)[AllTasksViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.all_tasks_fragment, container, false)
    }

    companion object {
        fun newInstance(): AllTasksFragment {
            return AllTasksFragment()
        }
    }
}
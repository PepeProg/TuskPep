package com.example.tusk.presentation.feature.all_tasks

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tusk.R
import com.example.tusk.presentation.MainApplication
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.adapters.ItemAdapter
import kotlinx.android.synthetic.main.all_tasks_fragment.*
import javax.inject.Inject

class AllTasksFragment: Fragment() {

    @Inject
    lateinit var allTasksUseCases: AllTasksUseCases

    private val viewModel: AllTasksViewModel by lazy {
        ViewModelProvider(
            this,
            AllTasksViewModel.Factory(allTasksUseCases)
        )[AllTasksViewModel::class.java]
    }

    private val taskAdapter = ItemAdapter<TaskItem>()
    private val fastAdapter = FastAdapter.with(taskAdapter)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        MainApplication.dagger.inject(this)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {

        inflater.inflate(R.menu.all_tasks_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.add_task -> {
                viewModel.addRandomTask()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.all_tasks_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        taskRecycler.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = fastAdapter
        }
        observeTasks()
    }

    private fun observeTasks() {
        viewModel.tasks.observe(viewLifecycleOwner) { tasks ->
            val items = tasks.map { taskVo ->
                TaskItem(taskVo)
            }
            taskAdapter.set(items)
        }
    }

    companion object {
        fun newInstance(): AllTasksFragment {
            return AllTasksFragment()
        }
    }
}
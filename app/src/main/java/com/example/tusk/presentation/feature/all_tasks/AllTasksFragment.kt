package com.example.tusk.presentation.feature.all_tasks

import android.graphics.Color
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tusk.R
import com.example.tusk.presentation.MainApplication
import com.example.tusk.presentation.feature.task_details.TaskDetailsDialogFragment
import com.example.tusk.presentation.navigation.Screens
import com.github.terrakok.cicerone.Router
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.adapters.ItemAdapter
import com.mikepenz.fastadapter.diff.FastAdapterDiffUtil
import com.mikepenz.fastadapter.drag.ItemTouchCallback
import com.mikepenz.fastadapter.swipe.SimpleSwipeCallback
import com.mikepenz.fastadapter.swipe_drag.SimpleSwipeDragCallback
import com.mikepenz.fastadapter.utils.DragDropUtil
import kotlinx.android.synthetic.main.all_tasks_fragment.*
import java.io.Serializable
import java.util.*
import javax.inject.Inject


class AllTasksFragment: Fragment(), ItemTouchCallback, SimpleSwipeCallback.ItemSwipeCallback {

    @Inject
    lateinit var allTasksUseCases: AllTasksUseCases

    @Inject
    lateinit var router: Router


    private lateinit var addTuskButton: MenuItem

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
        addTuskButton = menu.findItem(R.id.add_task)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.add_task -> {
                addTuskButton.isEnabled = false
                viewModel.addRandomTask()
                true
            }
            R.id.delete_all -> {
                viewModel.deleteAllTasks()
                true
            }
            R.id.notifications -> {
                router.navigateTo(Screens.NotificationsScreen())
                true
            }
            R.id.calendar -> {
                router.navigateTo(Screens.WeekDaysScreen())
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

        val touchCallback = SimpleSwipeDragCallback(
            itemTouchCallback = this,
            itemSwipeCallback = this,
            null,
            ItemTouchHelper.LEFT,
            Color.RED
        )
        val touchHelper = ItemTouchHelper(touchCallback)
        touchHelper.attachToRecyclerView(taskRecycler)
        observeTasks()
    }

    private fun observeTasks() {
        viewModel.tasks.observe(viewLifecycleOwner) { tasks ->
            addTuskButton.isEnabled = true
            val items = tasks.map(this::createTaskItem)
            val result = FastAdapterDiffUtil.calculateDiff(
                taskAdapter,
                items,
                TaskItem.DiffCallback()
            )
            val state = taskRecycler.layoutManager?.onSaveInstanceState()
            FastAdapterDiffUtil[taskAdapter] = result
            taskRecycler.layoutManager?.onRestoreInstanceState(state)
        }
    }

    override fun itemTouchOnMove(oldPosition: Int, newPosition: Int): Boolean {
        DragDropUtil.onMove(taskAdapter, oldPosition, newPosition)
        return true
    }

    override fun itemSwiped(position: Int, direction: Int) {
        viewModel.deleteTask(taskAdapter.itemList.items[position].model)
    }

    override fun itemTouchDropped(oldPosition: Int, newPosition: Int) {
        val taskVos = taskAdapter.itemList.items.map { taskItem ->
            taskItem.model
        }
        viewModel.itemPosChanged(taskVos, oldPosition, newPosition)
    }

    private fun createTaskItem(taskVo: TaskVo): TaskItem {
        return TaskItem(
            taskVo,
            this::showDetailsDialog
        )
    }

    private fun showDetailsDialog(source: View, taskVo: TaskVo) {
        val args = TaskDetailsDialogFragment.Companion.Arguments(
            source = source,
            name = taskVo.title,
            description = taskVo.description,
            deadline = taskVo.endDate,
            requestKey = REQUEST_KEY,
            taskVo.priority,
        )
        parentFragmentManager.setFragmentResultListener(REQUEST_KEY, this) { key, bundle ->
            handleResult(key, bundle)
        }

        TaskDetailsDialogFragment.newInstance(args).show(
            requireActivity().supportFragmentManager,
            "Kel"
        )
    }

    private fun handleResult(key: String, bundle: Bundle) {
        val result = bundle.getSerializable(REQUEST_KEY) as DetailsResult

        val taskVo = taskAdapter.itemList.items[result.pos].model
        viewModel.updateTask(taskVo, result.description, result.name, result.deadline)
    }


    companion object {
        fun newInstance(): AllTasksFragment {
            return AllTasksFragment()
        }

        private const val REQUEST_KEY = "request_key"

        data class DetailsResult(
            var name: String,
            var description: String,
            var deadline: Date,
            val pos: Int,
        ): Serializable
    }
}
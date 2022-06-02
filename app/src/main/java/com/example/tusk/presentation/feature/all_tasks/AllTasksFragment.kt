package com.example.tusk.presentation.feature.all_tasks

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tusk.R
import com.example.tusk.presentation.MainApplication
import com.example.tusk.presentation.feature.notifications.Receiver
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
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.all_tasks_fragment.*
import java.io.Serializable
import java.util.*
import javax.inject.Inject

const val CHANNEL_ID = "Kukis"

class AllTasksFragment: Fragment(), ItemTouchCallback, SimpleSwipeCallback.ItemSwipeCallback {

    @Inject
    lateinit var allTasksUseCases: AllTasksUseCases

    @Inject
    lateinit var router: Router

    private lateinit var notificationButton: ImageButton
    private lateinit var weekDaysButton: ImageButton
    private lateinit var currentDate: Date

    private val viewModel: AllTasksViewModel by lazy {
        ViewModelProvider(
            this,
            AllTasksViewModel.Factory(
                allTasksUseCases,
                currentDate
            )
        )[AllTasksViewModel::class.java]
    }

    private val taskAdapter = ItemAdapter<TaskItem>()
    private val fastAdapter = FastAdapter.with(taskAdapter)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        MainApplication.dagger.inject(this)
        currentDate = arguments?.get(DATE_KEY) as Date
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.all_tasks_fragment, container, false)

        notificationButton = requireActivity().findViewById(R.id.notification_button)
        weekDaysButton = requireActivity().findViewById(R.id.week_days_button)
        return view
    }

    override fun onStart() {
        super.onStart()

        add_task.setOnClickListener {
            add_task.isEnabled = false
            viewModel.addRandomTask()
        }

        delete_all.setOnClickListener {
            val taskVos = taskAdapter.itemList.items.map { taskItem ->
                taskItem.model
            }
            viewModel.deleteAllTasks(taskVos)
        }

        notificationButton.setOnClickListener {
            router.navigateTo(Screens.NotificationsScreen())
        }

        weekDaysButton.setOnClickListener {
            router.navigateTo(Screens.WeekDaysScreen())
        }
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
            add_task.isEnabled = true
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
        val taskVos = taskAdapter.itemList.items.map { taskItem ->
            taskItem.model
        }
        viewModel.deleteTask(taskAdapter.itemList.items[position].model, taskVos)
    }

    override fun itemTouchDropped(oldPosition: Int, newPosition: Int) {
        val taskVos = taskAdapter.itemList.items.map { taskItem ->
            taskItem.model
        }
        viewModel.validateAll(taskVos)
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

        val alarmManager = context?.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val intent = Intent(context, Receiver::class.java)
        intent.action = "MyBroadcastReceiverAction"
        val pendingIntent = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_IMMUTABLE)
        val msUntilTriggerHour = result.deadline.time
        Calendar.getInstance().apply { time = result.deadline }.get(
            Calendar.MILLISECOND
        ).toLong()
        Log.d("Kek","${result.deadline.time}")

        //val alarmClockInfo = AlarmManager.AlarmClockInfo(alarmTimeAtUTC,pendingIntent)

        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.M) {
            alarmManager.setAlarmClock(
                AlarmManager.AlarmClockInfo(msUntilTriggerHour, pendingIntent),
                pendingIntent
            )
        } else {
            alarmManager.setExactAndAllowWhileIdle(
                AlarmManager.RTC_WAKEUP,
                msUntilTriggerHour,
                pendingIntent
            )
        }

    }


    companion object {
        fun newInstance(date: Date): AllTasksFragment {
            val bundle = Bundle().apply {
                putSerializable(DATE_KEY, date)
            }
            return AllTasksFragment().apply {
                arguments = bundle
            }
        }

        private const val REQUEST_KEY = "request_key"
        private const val DATE_KEY = "date_key"

        data class DetailsResult(
            var name: String,
            var description: String,
            var deadline: Date,
            val pos: Int,
        ): Serializable
    }
}
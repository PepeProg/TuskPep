package com.example.tusk.presentation.feature.week_days

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.tusk.R
import com.example.tusk.presentation.MainApplication
import com.example.tusk.presentation.navigation.Screens
import com.github.terrakok.cicerone.Router
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.adapters.ItemAdapter
import com.mikepenz.fastadapter.diff.FastAdapterDiffUtil
import kotlinx.android.synthetic.main.week_days_fragment.*
import javax.inject.Inject

class WeekDaysFragment: Fragment() {
    @Inject
    lateinit var weekDaysUseCases: WeekDaysUseCases

    @Inject
    lateinit var router: Router

    private val weekDaysAdapter = ItemAdapter<WeekDayItem>()
    private val fastAdapter = FastAdapter.with(weekDaysAdapter)

    private val viewModel: WeekDayViewModel by lazy {
        ViewModelProvider(
            this,
            WeekDayViewModel.Factory(weekDaysUseCases)
        )[WeekDayViewModel::class.java]
    }

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
            R.id.notifications -> {
                router.navigateTo(Screens.NotificationsScreen())
                true
            }
            R.id.calendar -> {
                router.navigateTo(Screens.AllTasks())
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
        return inflater.inflate(R.layout.week_days_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        week_days_recycler.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = fastAdapter
        }

        observeTasks()
    }

    private fun observeTasks() {
        viewModel.days.observe(viewLifecycleOwner) { days ->
            val items = days.map(this::createWeekDayItem)
            val result = FastAdapterDiffUtil.calculateDiff(
                weekDaysAdapter,
                items,
                WeekDayItem.DiffCallback()
            )
            val state = week_days_recycler.layoutManager?.onSaveInstanceState()
            FastAdapterDiffUtil[weekDaysAdapter] = result
            week_days_recycler.layoutManager?.onRestoreInstanceState(state)
        }
    }

    private fun createWeekDayItem(weekDayVo: WeekDayVo): WeekDayItem {
        return WeekDayItem(
            weekDayVo,
            this::showTasksForDay
        )
    }

    private fun showTasksForDay(weekDayVo: WeekDayVo) {
        router.navigateTo(Screens.AllTasks())
    }

    companion object {
        fun newInstance(): WeekDaysFragment {
            return WeekDaysFragment()
        }
    }
}
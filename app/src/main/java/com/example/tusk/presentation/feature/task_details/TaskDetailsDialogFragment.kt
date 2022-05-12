package com.example.tusk.presentation.feature.task_details

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.TypedValue
import android.view.*
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import com.example.tusk.R
import com.example.tusk.presentation.feature.all_tasks.AllTasksFragment
import kotlinx.android.synthetic.main.fragment_dialog_task_details.*
import java.io.Serializable
import java.util.*


class TaskDetailsDialogFragment : DialogFragment() {
    private lateinit var source: View
    private lateinit var requestKey: String
    private lateinit var args: Arguments
    private var deadline = Date()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        args = arguments?.getSerializable(ARG_KEY) as Arguments
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_dialog_task_details, container, false)
    }

    override fun onStart() {
        super.onStart()

        source = args.source
        requestKey = args.requestKey
        setDialogPosition()

        name_edit_text.setText(args.name)
        descr_edit_text.setText(args.description)
        deadline_button.text = args.deadline.toString()
        submit_button.setOnClickListener {
            onSubmitClick()
        }
        deadline_button.setOnClickListener {
            onDeadlineClick()
        }
    }

    private fun onSubmitClick() {
        val result = AllTasksFragment.Companion.DetailsResult(
            name = name_edit_text.text.toString(),
            description = descr_edit_text.text.toString(),
            deadline = deadline,
            pos = args.pos,
        )
        val resultBundle = Bundle().apply {
            putSerializable(requestKey, result)
        }

        parentFragmentManager.setFragmentResult(requestKey, resultBundle)
        dialog?.dismiss()
    }

    private fun onDeadlineClick() {
        val initialTime = Calendar.getInstance().apply { time = deadline }

        DatePickerDialog(
            requireContext(),
            DateListener(),
            initialTime.get(Calendar.YEAR),
            initialTime.get(Calendar.MONTH),
            initialTime.get(Calendar.DAY_OF_MONTH)
        ).show()
    }

    private inner class DateListener: DatePickerDialog.OnDateSetListener {
        override fun onDateSet(p0: DatePicker?, year: Int, month: Int, day: Int) {
            val calendar = Calendar.getInstance()
            calendar.apply {
                set(Calendar.YEAR, year)
                set(Calendar.MONTH, month)
                set(Calendar.DAY_OF_MONTH, day)
            }
            deadline = calendar.time
        }
    }

    private fun setDialogPosition() {
        if (!this::source.isInitialized) {
            return
        }

        val location = IntArray(2)
        source.getLocationOnScreen(location)

        val sourceY = location[1]
        val window = dialog?.window
        val params = window?.attributes

        window?.apply {
            setGravity(Gravity.TOP or Gravity.START)
            setLayout(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT
            )
        }

        params?.apply {
            y = sourceY + dpToPx(50F)
            dimAmount = 0.10F
            flags = params.flags
                .or(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
        }

        window?.attributes = params
    }

    private fun dpToPx(valueInDp: Float): Int {
        val metrics = activity?.resources?.displayMetrics

        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, valueInDp, metrics).toInt()
    }

    companion object {

        data class Arguments(
            val source: View,
            var name: String,
            var description: String,
            var deadline: Date,
            val requestKey: String,
            val pos: Int,
        ): Serializable

        fun newInstance(args: Arguments): TaskDetailsDialogFragment {
            val argBundle = Bundle().apply {
                putSerializable(
                    ARG_KEY,
                    args
                )
            }

            return TaskDetailsDialogFragment().apply {
                arguments = argBundle
            }
        }

        private const val ARG_KEY = "ARG_KEY"
    }
}
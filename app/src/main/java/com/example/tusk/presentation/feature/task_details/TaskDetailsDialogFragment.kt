package com.example.tusk.presentation.feature.task_details

import android.os.Bundle
import android.util.TypedValue
import android.view.*
import androidx.fragment.app.DialogFragment
import com.example.tusk.R


class TaskDetailsDialogFragment() : DialogFragment() {
    private lateinit var source: View

    constructor(source: View): this() {
        this.source = source
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

        setDialogPosition()
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
        fun newInstance(source: View): TaskDetailsDialogFragment {
            return TaskDetailsDialogFragment(source)
        }
    }
}
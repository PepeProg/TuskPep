package com.example.tusk.presentation.feature.all_tasks

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tusk.R
import com.mikepenz.fastadapter.items.ModelAbstractItem

class TaskItem(task: TaskVo): ModelAbstractItem<TaskVo, TaskItem.ViewHolder>(task) {

    override val layoutRes: Int = R.layout.item_task

    override val type: Int = R.id.task_item

    override fun getViewHolder(v: View) = ViewHolder(v)

    override fun bindView(holder: ViewHolder, payloads: List<Any>) {
        super.bindView(holder, payloads)

        holder.apply {
            title.text = model.title
            startDate.text = model.startDate.toString()
            endDate.text = model.endDate.toString()
        }
    }

    class ViewHolder(taskView: View) : RecyclerView.ViewHolder(taskView) {
        val title: TextView = taskView.findViewById(R.id.title)
        val startDate: TextView = taskView.findViewById(R.id.start_date)
        val endDate: TextView = taskView.findViewById(R.id.end_date)
    }
}
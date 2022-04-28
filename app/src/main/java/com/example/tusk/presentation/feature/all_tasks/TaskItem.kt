package com.example.tusk.presentation.feature.all_tasks

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tusk.R
import com.mikepenz.fastadapter.drag.IDraggable
import com.mikepenz.fastadapter.items.ModelAbstractItem

class TaskItem(
    task: TaskVo,
    private val onClickListener: (View, TaskVo) -> Unit,
): ModelAbstractItem<TaskVo, TaskItem.ViewHolder>(task), IDraggable {

    override val layoutRes: Int = R.layout.item_task

    override val isDraggable: Boolean = true

    override val type: Int = R.id.task_item

    override var identifier: Long
        get() = model.id.mostSignificantBits and Long.MAX_VALUE
        set(value) {}

    override fun getViewHolder(v: View) = ViewHolder(v)

    override fun bindView(holder: ViewHolder, payloads: List<Any>) {
        super.bindView(holder, payloads)

        holder.apply {
            title.text = model.title
            startDate.text = model.startDate.toString()
            endDate.text = model.priority.toString()
            itemView.setOnClickListener {
                onClickListener(itemView, model)
            }
        }
    }

    class ViewHolder(taskView: View) : RecyclerView.ViewHolder(taskView) {
        val title: TextView = taskView.findViewById(R.id.title)
        val startDate: TextView = taskView.findViewById(R.id.start_date)
        val endDate: TextView = taskView.findViewById(R.id.end_date)
    }

    class DiffCallback : com.mikepenz.fastadapter.diff.DiffCallback<TaskItem> {
        override fun areContentsTheSame(oldItem: TaskItem, newItem: TaskItem): Boolean {
            return oldItem.model.id == newItem.model.id
        }

        override fun areItemsTheSame(oldItem: TaskItem, newItem: TaskItem): Boolean {
            return oldItem === newItem
        }

        override fun getChangePayload(
            oldItem: TaskItem,
            oldItemPosition: Int,
            newItem: TaskItem,
            newItemPosition: Int
        ): Any? {
            return null
        }
    }
}
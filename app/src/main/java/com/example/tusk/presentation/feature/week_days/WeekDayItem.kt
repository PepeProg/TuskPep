package com.example.tusk.presentation.feature.week_days

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.tusk.R
import com.mikepenz.fastadapter.items.ModelAbstractItem
import java.text.SimpleDateFormat
import java.util.*

class WeekDayItem(
    weekDayVo: WeekDayVo,
    private val onClickListener: (WeekDayVo) -> Unit
): ModelAbstractItem<WeekDayVo, WeekDayItem.ViewHolder>(weekDayVo) {

    override val type = R.id.week_day_item
    override val layoutRes = R.layout.item_week_day

    override var identifier: Long
        get() = model.order_id
        set(value) {}

    override fun getViewHolder(v: View) = ViewHolder(v)

    override fun bindView(holder: WeekDayItem.ViewHolder, payloads: List<Any>) {
        super.bindView(holder, payloads)

        holder.apply {
            title.text = model.name
            weekDate.text = SimpleDateFormat("dd:MM:y", Locale.ENGLISH).format(model.date)
            itemView.setOnClickListener {
                onClickListener(model)
            }
        }
    }

    class ViewHolder(weekDayView: View) : RecyclerView.ViewHolder(weekDayView) {
        val title: TextView = weekDayView.findViewById(R.id.week_title)
        val weekDate: TextView = weekDayView.findViewById(R.id.week_date)
    }

    class DiffCallback : com.mikepenz.fastadapter.diff.DiffCallback<WeekDayItem> {
        override fun areContentsTheSame(oldItem: WeekDayItem, newItem: WeekDayItem): Boolean {
            return (oldItem.model.order_id == newItem.model.order_id) &&
                    (oldItem.model.weatherType == newItem.model.weatherType) &&
                    (oldItem.model.degree == newItem.model.degree)
        }

        override fun areItemsTheSame(oldItem: WeekDayItem, newItem: WeekDayItem): Boolean {
            return oldItem === newItem
        }

        override fun getChangePayload(
            oldItem: WeekDayItem,
            oldItemPosition: Int,
            newItem: WeekDayItem,
            newItemPosition: Int
        ): Any? {
            return null
        }
    }
}
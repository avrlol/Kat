package com.student.scheduler.adapters

import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.student.scheduler.R
import com.student.scheduler.model.TaskUi

/**
 * Renders task cards on Home / Tasks screens.
 * [onCheckedChange] reports checkbox toggles back to the fragment/ViewModel
 * (from week 6 this will trigger a Room update instead of just a callback).
 */
class TaskAdapter(
    private val tasks: List<TaskUi>,
    private val onCheckedChange: (position: Int, isChecked: Boolean) -> Unit
) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    inner class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val checkbox: CheckBox = itemView.findViewById(R.id.checkbox_done)
        val title: TextView = itemView.findViewById(R.id.text_task_title)
        val due: TextView = itemView.findViewById(R.id.text_due_label)
        val tag: TextView = itemView.findViewById(R.id.text_tag)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_task_card, parent, false)
        return TaskViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = tasks[position]

        holder.title.text = task.title
        holder.due.text = task.dueLabel
        holder.tag.text = task.tagName

        // Tint the pill background drawable with this task's subject color.
        val pill = holder.tag.background.mutate() as GradientDrawable
        pill.setColor(holder.itemView.context.getColor(task.tagColor))

        holder.checkbox.setOnCheckedChangeListener(null)
        holder.checkbox.isChecked = task.isDone
        holder.checkbox.setOnCheckedChangeListener { _, isChecked ->
            onCheckedChange(holder.bindingAdapterPosition, isChecked)
        }
    }

    override fun getItemCount(): Int = tasks.size
}

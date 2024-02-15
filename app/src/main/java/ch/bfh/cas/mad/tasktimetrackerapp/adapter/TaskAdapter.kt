package ch.bfh.cas.mad.tasktimetrackerapp.adapter

import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ch.bfh.cas.mad.tasktimetrackerapp.R
import ch.bfh.cas.mad.tasktimetrackerapp.Entities.Task
import ch.bfh.cas.mad.tasktimetrackerapp.activities.DetailTaskActivity

class TaskAdapter(private val tasks: MutableList<Task>) : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    inner class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView: TextView = itemView.findViewById(R.id.textViewTaskName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.task_item, parent, false)
        return TaskViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        val task = tasks[position]
        holder.textView.text = task.name

        // Set background color based on position
        if (position % 2 == 0) {
            holder.itemView.setBackgroundColor(Color.LTGRAY) // Change this to your desired color for even positions
        } else {
            holder.itemView.setBackgroundColor(Color.WHITE) // Change this to your desired color for odd positions
        }

        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, DetailTaskActivity::class.java)
            intent.putExtra("taskId", task.id)
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount() = tasks.size
}

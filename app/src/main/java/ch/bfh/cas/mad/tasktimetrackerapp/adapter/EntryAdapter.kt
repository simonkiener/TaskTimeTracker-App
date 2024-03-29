package ch.bfh.cas.mad.tasktimetrackerapp.adapter

import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ch.bfh.cas.mad.tasktimetrackerapp.persistence.Entry
import ch.bfh.cas.mad.tasktimetrackerapp.R
import ch.bfh.cas.mad.tasktimetrackerapp.activities.DetailEntryActivity
import ch.bfh.cas.mad.tasktimetrackerapp.persistence.Task
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

class EntryAdapter(
    private var entries: List<Entry>,
    private var tasks: List<Task>
) : RecyclerView.Adapter<EntryAdapter.EntryViewHolder>() {

    inner class EntryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val entryName: TextView = itemView.findViewById(R.id.entryName)
        val taskName: TextView = itemView.findViewById(R.id.taskName)
        val timeStamp: TextView = itemView.findViewById(R.id.timeStamp)
        val editButton: Button = itemView.findViewById(R.id.editButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EntryViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.entry_item, parent, false)
        return EntryViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: EntryViewHolder, position: Int) {
        val entry = entries[position]
        holder.entryName.text = entry.description
        holder.taskName.text = getTaskName(entry.taskId)  // ToDo: change method to database query
        holder.timeStamp.text = getTimeStampFormatted(entry.timeStamp)

        // Set background color based on position
        if (position % 2 == 0) {
            holder.itemView.setBackgroundColor(Color.LTGRAY) // Change this to your desired color for even positions
        } else {
            holder.itemView.setBackgroundColor(Color.WHITE) // Change this to your desired color for odd positions
        }

        holder.editButton.setOnClickListener {
            holder.editButton.setOnClickListener {
                val context = holder.itemView.context
                val intent = Intent(context, DetailEntryActivity::class.java)
                intent.putExtra("entryId", entry.id)
                intent.putExtra("task", entry.description)
                //intent.putExtra("project", getProjectName(entry.taskId))
                intent.putExtra("timestamp", getTimeStampFormatted(entry.timeStamp))
                context.startActivity(intent)
            }
        }
    }

    override fun getItemCount() = entries.size

    private fun getTimeStampFormatted(timeStamp: Long): String {
        val currentDateTime = LocalDateTime.ofEpochSecond(timeStamp, 0, ZoneOffset.UTC)
        val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss")
        return currentDateTime.format(formatter)
    }

    private fun getTaskName(taskId: Int): String {
        return tasks.find { it.taskId == taskId }?.name ?: ""
    }
}
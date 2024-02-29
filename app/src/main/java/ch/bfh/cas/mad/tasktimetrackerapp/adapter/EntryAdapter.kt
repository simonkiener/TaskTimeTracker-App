package ch.bfh.cas.mad.tasktimetrackerapp.adapter

import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ch.bfh.cas.mad.tasktimetrackerapp.persistence.DataStore.getEntriesForTask
import ch.bfh.cas.mad.tasktimetrackerapp.persistence.DataStore.getProjectName
import ch.bfh.cas.mad.tasktimetrackerapp.persistence.Entry
import ch.bfh.cas.mad.tasktimetrackerapp.R
import ch.bfh.cas.mad.tasktimetrackerapp.activities.DetailEntryActivity
import ch.bfh.cas.mad.tasktimetrackerapp.persistence.DataStore.getTaskName
import java.sql.Timestamp

class EntryAdapter(
    private var entries: List<Entry>
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
        holder.timeStamp.text = Timestamp(entry.timeStamp).toString()

        // Set background color based on position
        if (position % 2 == 0) {
            holder.itemView.setBackgroundColor(Color.LTGRAY) // Change this to your desired color for even positions
            holder.entryName.text = "Start"
        } else {
            holder.itemView.setBackgroundColor(Color.WHITE) // Change this to your desired color for odd positions
            holder.entryName.text = "Stop"
        }

        holder.editButton.setOnClickListener {
            holder.editButton.setOnClickListener {
                val context = holder.itemView.context
                val intent = Intent(context, DetailEntryActivity::class.java)
                intent.putExtra("task", entry.description)
                intent.putExtra("project", getProjectName(entry.taskId))
                context.startActivity(intent)
            }
        }
    }

    override fun getItemCount() = entries.size

    fun filterList(task: String, project: String) {
        val filteredList = entries.filter { it.description == task && getProjectName(it.taskId) == project }
        this.entries = filteredList
        notifyDataSetChanged()
    }

    fun updateEntries(taskId: Int) {
        val entries = getEntriesForTask(taskId)
        this.entries = entries
        notifyDataSetChanged()
    }
}
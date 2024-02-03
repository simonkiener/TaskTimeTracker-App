package ch.bfh.cas.mad.tasktimetrackerapp

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ch.bfh.cas.mad.tasktimetrackerapp.DataStore.getEntriesForTask
import ch.bfh.cas.mad.tasktimetrackerapp.DataStore.getProjectName

class EntryAdapter(private var entries: List<Entry>) : RecyclerView.Adapter<EntryAdapter.EntryViewHolder>() {

    inner class EntryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val taskName: TextView = itemView.findViewById(R.id.taskName)
        val projectName: TextView = itemView.findViewById(R.id.projectName)
        val editButton: Button = itemView.findViewById(R.id.editButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EntryViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.entry_item, parent, false)
        return EntryViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: EntryViewHolder, position: Int) {
        val entry = entries[position]
        holder.taskName.text = entry.description
        holder.projectName.text = getProjectName(entry.taskId)

        // Set background color based on position
        if (position % 2 == 0) {
            holder.itemView.setBackgroundColor(Color.LTGRAY) // Change this to your desired color for even positions
        } else {
            holder.itemView.setBackgroundColor(Color.WHITE) // Change this to your desired color for odd positions
        }

        holder.editButton.setOnClickListener {
            // Implement your edit functionality here
        }
    }

    override fun getItemCount() = entries.size

    fun filterList(task: String, project: String) {
        val filteredList = entries.filter { it.description == task && DataStore.getProjectName(it.taskId) == project }
        this.entries = filteredList
        notifyDataSetChanged()
    }
}
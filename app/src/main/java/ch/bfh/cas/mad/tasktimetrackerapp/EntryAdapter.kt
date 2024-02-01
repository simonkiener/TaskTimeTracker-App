package ch.bfh.cas.mad.tasktimetrackerapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class EntryAdapter(private val entries: List<Entry>) : RecyclerView.Adapter<EntryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EntryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.entry_item, parent, false)
        return EntryViewHolder(view)
    }

    override fun onBindViewHolder(holder: EntryViewHolder, position: Int) {
        val entry = entries[position]
        holder.taskName.text = entry.taskName
        holder.projectName.text = entry.projectName
    }

    override fun getItemCount() = entries.size
}

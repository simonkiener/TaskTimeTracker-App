package ch.bfh.cas.mad.tasktimetrackerapp

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class EntryViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val taskName: TextView = view.findViewById(R.id.taskName)
    val projectName: TextView = view.findViewById(R.id.projectName)
}

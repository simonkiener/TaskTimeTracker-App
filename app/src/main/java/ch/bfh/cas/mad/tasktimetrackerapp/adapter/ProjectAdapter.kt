package ch.bfh.cas.mad.tasktimetrackerapp.adapter

import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ch.bfh.cas.mad.tasktimetrackerapp.Project
import ch.bfh.cas.mad.tasktimetrackerapp.R
import ch.bfh.cas.mad.tasktimetrackerapp.activities.DetailProjectActivity

//import ch.bfh.cas.mad.tasktimetrackerapp.activities.DetailProjectActivity

class ProjectAdapter(private val projects: MutableList<Project>) : RecyclerView.Adapter<ProjectAdapter.ProjectViewHolder>() {

    inner class ProjectViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textView: TextView = itemView.findViewById(R.id.textViewProjectName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProjectViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.project_item, parent, false)
        return ProjectViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ProjectViewHolder, position: Int) {
        val project = projects[position]
        holder.textView.text = project.name

        // Set background color based on position
        if (position % 2 == 0) {
            holder.itemView.setBackgroundColor(Color.LTGRAY) // Change this to your desired color for even positions
        } else {
            holder.itemView.setBackgroundColor(Color.WHITE) // Change this to your desired color for odd positions
        }

        holder.itemView.setOnClickListener {
            val intent = Intent(holder.itemView.context, DetailProjectActivity::class.java)
            intent.putExtra("projectId", project.id)
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount() = projects.size
}
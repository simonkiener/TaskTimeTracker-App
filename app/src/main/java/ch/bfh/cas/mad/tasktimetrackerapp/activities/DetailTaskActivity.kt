package ch.bfh.cas.mad.tasktimetrackerapp.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ch.bfh.cas.mad.tasktimetrackerapp.DataStore
import ch.bfh.cas.mad.tasktimetrackerapp.Entry
import ch.bfh.cas.mad.tasktimetrackerapp.EntryAdapter
import ch.bfh.cas.mad.tasktimetrackerapp.R

class DetailTaskActivity : ComponentActivity() {

    private lateinit var taskName: TextView
    private lateinit var showAllEntriesButton: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detailtask)

        val taskId = intent.getIntExtra("taskId", 2)

        val task = findViewById<TextView>(R.id.taskName)
        val showEntries = findViewById<Button>(R.id.btnShowAllEntries)

        task.text = DataStore.getTaskName(taskId)
        val entriesRecyclerView = findViewById<RecyclerView>(R.id.entriesRecyclerView)
        entriesRecyclerView.layoutManager = LinearLayoutManager(this)
        entriesRecyclerView.adapter = EntryAdapter(getEntries(taskId))

        showEntries.setOnClickListener {
            val intent = Intent(this, EntriesOverviewActivity::class.java)
            startActivity(intent)
        }
    }

    private fun getEntries(taskId: Int): List<Entry> {
        // Hier sollten Sie die tatsächlichen Einträge abrufen.
        return DataStore.getEntriesForTask(taskId)
    }
}
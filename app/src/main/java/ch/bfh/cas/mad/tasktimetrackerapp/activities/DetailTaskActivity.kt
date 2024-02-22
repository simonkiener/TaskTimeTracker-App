package ch.bfh.cas.mad.tasktimetrackerapp.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ch.bfh.cas.mad.tasktimetrackerapp.persistence.DataStore
import ch.bfh.cas.mad.tasktimetrackerapp.persistence.Entry
import ch.bfh.cas.mad.tasktimetrackerapp.adapter.EntryAdapter
import ch.bfh.cas.mad.tasktimetrackerapp.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

class DetailTaskActivity : ComponentActivity() {

    private lateinit var taskName: TextView
    private lateinit var showAllEntriesButton: Button
    private lateinit var backButton: FloatingActionButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detailtask)

        val taskId = intent.getIntExtra("taskId", 2)

        val task = findViewById<TextView>(R.id.taskName)
        showAllEntriesButton = findViewById<Button>(R.id.btnShowAllEntries)
        backButton = findViewById<FloatingActionButton>(R.id.fabBack)

        task.text = DataStore.getTaskName(taskId)
        val entriesRecyclerView = findViewById<RecyclerView>(R.id.entriesRecyclerView)
        entriesRecyclerView.layoutManager = LinearLayoutManager(this)
        entriesRecyclerView.adapter = EntryAdapter(getEntries(taskId))

        showAllEntriesButton.setOnClickListener {
            val intent = Intent(this, EntriesOverviewActivity::class.java)
            startActivity(intent)
        }

        backButton.setOnClickListener {
            finish()
        }
    }

    private fun getEntries(taskId: Int): List<Entry> {
        // Hier sollten Sie die tatsächlichen Einträge abrufen.
        return DataStore.getEntriesForTask(taskId)
    }
}
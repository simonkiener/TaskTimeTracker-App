package ch.bfh.cas.mad.tasktimetrackerapp.activities

import android.os.Bundle
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ch.bfh.cas.mad.tasktimetrackerapp.Entry
import ch.bfh.cas.mad.tasktimetrackerapp.EntryAdapter
import ch.bfh.cas.mad.tasktimetrackerapp.R
import ch.bfh.cas.mad.tasktimetrackerapp.ui.theme.TaskTimeTrackerAppTheme

class DetailTaskActivity : ComponentActivity() {

    private lateinit var taskName: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detailtask)

        val taskName = intent.getStringExtra("taskName")

        val task = findViewById<TextView>(R.id.taskName)

        task.text = taskName
        val entriesRecyclerView = findViewById<RecyclerView>(R.id.entriesRecyclerView)
        entriesRecyclerView.layoutManager = LinearLayoutManager(this)
        entriesRecyclerView.adapter = EntryAdapter(getEntries())
    }

    private fun getEntries(): List<Entry> {
        // Hier sollten Sie die tatsächlichen Einträge abrufen.
        // Für dieses Beispiel geben wir eine leere Liste zurück.
        return listOf(
            Entry("Task1", "Project1"),
            Entry("Task2", "Project2"),
            Entry("Task3", "Project3"),
            Entry("Task4", "Project4"),
            Entry("Task5", "Project5")
        )
    }
}
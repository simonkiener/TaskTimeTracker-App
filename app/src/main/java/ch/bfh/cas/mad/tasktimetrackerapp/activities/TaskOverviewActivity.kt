package ch.bfh.cas.mad.tasktimetrackerapp.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
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
import ch.bfh.cas.mad.tasktimetrackerapp.R
import ch.bfh.cas.mad.tasktimetrackerapp.Task
import ch.bfh.cas.mad.tasktimetrackerapp.TaskAdapter
import ch.bfh.cas.mad.tasktimetrackerapp.ui.theme.TaskTimeTrackerAppTheme
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.util.UUID

class TaskOverviewActivity : ComponentActivity() {

    private lateinit var addButton: FloatingActionButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_taskoverview)

        addButton = findViewById(R.id.fabAddTask)

        val tasks = listOf(
            Task(UUID.randomUUID(), "Task1", 1),
            Task(UUID.randomUUID(), "Task2", 1),
            Task(UUID.randomUUID(), "Task3", 1),
            Task(UUID.randomUUID(), "Task4", 1),
            Task(UUID.randomUUID(), "Task5", 1),
            Task(UUID.randomUUID(), "Task6", 1),
            Task(UUID.randomUUID(), "Task7", 1),
            Task(UUID.randomUUID(), "Task8", 1),
            Task(UUID.randomUUID(), "Task9", 1),
            Task(UUID.randomUUID(), "Task10", 1))
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewTasks)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = TaskAdapter(tasks)

        addButton.setOnClickListener {
            val intent = Intent(this, AddTaskActivity::class.java)
            startActivity(intent)
        }
    }
}
package ch.bfh.cas.mad.tasktimetrackerapp.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ch.bfh.cas.mad.tasktimetrackerapp.adapter.EntryAdapter
import ch.bfh.cas.mad.tasktimetrackerapp.R
import ch.bfh.cas.mad.tasktimetrackerapp.persistence.ProjectRepository
import ch.bfh.cas.mad.tasktimetrackerapp.persistence.TTTDatabaseProvider
import ch.bfh.cas.mad.tasktimetrackerapp.persistence.TaskRepository
import ch.bfh.cas.mad.tasktimetrackerapp.viewModel.TaskDetailViewModel
import ch.bfh.cas.mad.tasktimetrackerapp.viewModel.TaskDetailViewModelFactory
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class DetailTaskActivity : ComponentActivity() {

    private lateinit var viewModel: TaskDetailViewModel
    private lateinit var showAllEntriesButton: Button
    private lateinit var backButton: FloatingActionButton
    private lateinit var taskNameView: TextView
    private lateinit var projectNameView: TextView
    private var taskId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detailtask)

        val viewModelProvider = ViewModelProvider(
            this,
            // ToDo: simplify parameters!
            TaskDetailViewModelFactory(ProjectRepository(TTTDatabaseProvider.get(this).getProjectDao(), TTTDatabaseProvider.get(this).getEntryDao()), TaskRepository(TTTDatabaseProvider.get(this).getTaskDao(), TTTDatabaseProvider.get(this).getEntryDao()))
        )

        viewModel = viewModelProvider[TaskDetailViewModel::class.java]

        showAllEntriesButton = findViewById<Button>(R.id.btnShowAllEntries)
        backButton = findViewById<FloatingActionButton>(R.id.fabBack)
        taskNameView = findViewById(R.id.TextViewTaskName)
        projectNameView = findViewById(R.id.TextViewTaskProjectName)

        taskId = intent.getIntExtra("taskId", -1)

        // getEntriesForTask
        val recyclerView = findViewById<RecyclerView>(R.id.entriesRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        lifecycleScope.launch {
            viewModel.entries.collectLatest { entries ->
                val adapter = EntryAdapter(entries = entries, tasks = viewModel.tasks.value)  // ToDo: check, if tasks are really needed, maybe pass empty list?
                recyclerView.adapter = adapter
            }
        }

        // getTaskName
        lifecycleScope.launch {
            viewModel.taskName.collectLatest { taskName ->
                taskNameView.text = taskName
            }
        }

//        // getTasks
//        lifecycleScope.launch {
//            viewModel.tasks.collectLatest { tasks ->
//                val hans = tasks
//            }
//        }

        // getProjectName
        lifecycleScope.launch {
            viewModel.projectName.collectLatest { projectName ->
                projectNameView.text = projectName
            }
        }

        showAllEntriesButton.setOnClickListener {
            val intent = Intent(this, EntryOverviewActivity::class.java)
            intent.putExtra("taskId", taskId)
            startActivity(intent)
        }

        backButton.setOnClickListener {
            finish()
        }
    }

    override fun onResume() {
        super.onResume()
        // ToDo: think about merging all together into one single method e.g. "viewModel.ReadTaskData()"
        viewModel.getEntriesForTask(taskId)
        viewModel.getTaskName(taskId)
        viewModel.getProjectName(taskId)
        viewModel.getTasks()
    }
}
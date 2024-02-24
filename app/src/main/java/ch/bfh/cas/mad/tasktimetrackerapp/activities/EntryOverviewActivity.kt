package ch.bfh.cas.mad.tasktimetrackerapp.activities

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ch.bfh.cas.mad.tasktimetrackerapp.adapter.EntryAdapter
import ch.bfh.cas.mad.tasktimetrackerapp.R
import ch.bfh.cas.mad.tasktimetrackerapp.persistence.EntryRepository
import ch.bfh.cas.mad.tasktimetrackerapp.persistence.Project
import ch.bfh.cas.mad.tasktimetrackerapp.persistence.ProjectRepository
import ch.bfh.cas.mad.tasktimetrackerapp.persistence.TTTDatabaseProvider
import ch.bfh.cas.mad.tasktimetrackerapp.persistence.Task
import ch.bfh.cas.mad.tasktimetrackerapp.persistence.TaskRepository
import ch.bfh.cas.mad.tasktimetrackerapp.viewModel.EntryOverviewViewModel
import ch.bfh.cas.mad.tasktimetrackerapp.viewModel.EntryOverviewViewModelFactory
import ch.bfh.cas.mad.tasktimetrackerapp.viewModel.ProjectOverviewViewModel
import ch.bfh.cas.mad.tasktimetrackerapp.viewModel.ProjectOverviewViewModelFactory
import ch.bfh.cas.mad.tasktimetrackerapp.viewModel.TaskOverviewViewModel
import ch.bfh.cas.mad.tasktimetrackerapp.viewModel.TaskOverviewViewModelFactory
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.Calendar

class EntryOverviewActivity : ComponentActivity() {

    private lateinit var viewModel: EntryOverviewViewModel
    private lateinit var projectViewModel: ProjectOverviewViewModel
    private lateinit var taskViewModel: TaskOverviewViewModel
    private lateinit var backButton: FloatingActionButton
    private lateinit var projectName: AutoCompleteTextView
    private lateinit var taskName: AutoCompleteTextView
    private lateinit var project: Project
    private lateinit var task: Task
    private var projectId: Int = -1
    private var taskId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_entriesoverview)

        val viewModelProvider = ViewModelProvider(
            this,
            EntryOverviewViewModelFactory(EntryRepository(TTTDatabaseProvider.get(this).getEntryDao()))
        )

        val projectViewModelProvider = ViewModelProvider(
            this,
            ProjectOverviewViewModelFactory(ProjectRepository(TTTDatabaseProvider.get(this).getProjectDao(), TTTDatabaseProvider.get(this).getEntryDao()))
        )

        val taskViewModelProvider = ViewModelProvider(
            this,
            TaskOverviewViewModelFactory(TaskRepository(TTTDatabaseProvider.get(this).getTaskDao(), TTTDatabaseProvider.get(this).getEntryDao()))
        )

        viewModel = viewModelProvider[EntryOverviewViewModel::class.java]
        projectViewModel = projectViewModelProvider[ProjectOverviewViewModel::class.java]
        taskViewModel = taskViewModelProvider[TaskOverviewViewModel::class.java]

        backButton = findViewById(R.id.fabBack)

        projectName = findViewById(R.id.projectName)
        taskName = findViewById(R.id.taskName)

        // getEntriesFor...
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewEntries)
        recyclerView.layoutManager = LinearLayoutManager(this)
        lifecycleScope.launch {
            viewModel.entries.collectLatest { entries ->
                val adapter = EntryAdapter(entries = entries)
                recyclerView.adapter = adapter
            }
        }

        // getAllProjects
        lifecycleScope.launch {
            projectViewModel.projects.collectLatest { projects ->
                val adapter = ArrayAdapter(this@EntryOverviewActivity, android.R.layout.simple_dropdown_item_1line, projects)
                projectName.setAdapter(adapter)
            }
        }

        // getAllTasks
        lifecycleScope.launch {
            taskViewModel.tasks.collectLatest { tasks ->
                val adapter = ArrayAdapter(this@EntryOverviewActivity, android.R.layout.simple_dropdown_item_1line, tasks)
                taskName.setAdapter(adapter)
            }
        }



        val textViewStartDate = findViewById<TextView>(R.id.textViewStartDate)
        val textViewEndDate = findViewById<TextView>(R.id.textViewEndDate)

        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        textViewStartDate.setOnClickListener {
            val datePickerDialog = DatePickerDialog(this,
                { _, selectedYear, selectedMonth, selectedDay ->
                    textViewStartDate.text = "$selectedDay/${selectedMonth + 1}/$selectedYear"
                }, year, month, day)
            datePickerDialog.show()
        }

        textViewEndDate.setOnClickListener {
            val datePickerDialog = DatePickerDialog(this,
                { _, selectedYear, selectedMonth, selectedDay ->
                    textViewEndDate.text = "$selectedDay/${selectedMonth + 1}/$selectedYear"
                }, year, month, day)
            datePickerDialog.show()
        }

        projectName.setOnItemClickListener{ parent, _, position, _ ->
            val item = parent.getItemAtPosition(position)
            if (item is Project) {
                val project: Project = item
                this@EntryOverviewActivity.project = project
                this@EntryOverviewActivity.projectId = project.id
                this@EntryOverviewActivity.taskId = -1
            }
        }

        taskName.setOnItemClickListener{ parent, _, position, _ ->
            val item = parent.getItemAtPosition(position)
            if (item is Task) {
                val task: Task = item
                this@EntryOverviewActivity.task = task
                this@EntryOverviewActivity.taskId = task.id
                this@EntryOverviewActivity.projectId = task.projectId
            }
        }

        backButton.setOnClickListener {
            finish()
        }
    }

    override fun onResume() {
        super.onResume()
        if (taskId > 0) {
            viewModel.getEntriesForTask(taskId)
        } else if (projectId > 0) {
            viewModel.getEntriesForProject(projectId)
        } else {
            viewModel.getAllEntries()
        }

        projectViewModel.getAllProjects()
        taskViewModel.getAllTasks()


    }
}
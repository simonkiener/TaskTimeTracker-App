package ch.bfh.cas.mad.tasktimetrackerapp.activities

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ch.bfh.cas.mad.tasktimetrackerapp.R
import ch.bfh.cas.mad.tasktimetrackerapp.adapter.EntryAdapter
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
    private lateinit var addButton: FloatingActionButton
    private lateinit var backButton: FloatingActionButton
    private lateinit var projectName: AutoCompleteTextView
    private lateinit var taskName: AutoCompleteTextView
    private lateinit var totalTimeView: TextView
    private lateinit var project: Project
    private lateinit var task: Task
    private var startDate: Long = Long.MIN_VALUE
    private var endDate: Long = Long.MAX_VALUE
    private var projectId: Int = -1
    private var taskId: Int = -1
    private var projectChosen: Boolean = false
    private var taskChosen: Boolean = false

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

        addButton = findViewById(R.id.fabAddEntry)
        backButton = findViewById(R.id.fabBack)

        projectName = findViewById(R.id.projectName)
        taskName = findViewById(R.id.taskName)
        totalTimeView = findViewById(R.id.textViewTotalTime)

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

        // getTotalTime
        lifecycleScope.launch {
            viewModel.totalTime.collectLatest { totalTime ->
                totalTimeView.text = getTotalTimeText(totalTime)
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
                    calendar.set(selectedYear, selectedMonth + 1, selectedDay, 0, 0, 0)

                    println("------------------------------------------------")
                    println("Year: " + selectedYear.toString())
                    println("Month: " + (selectedMonth + 1).toString())
                    println("Day: " + selectedDay.toString())
                    println("------------------------------------------------")

                    startDate = calendar.timeInMillis

                    getEntries()
                }, year, month, day)
            datePickerDialog.show()
        }

        textViewEndDate.setOnClickListener {
            val datePickerDialog = DatePickerDialog(this,
                { _, selectedYear, selectedMonth, selectedDay ->
                    textViewEndDate.text = "$selectedDay/${selectedMonth + 1}/$selectedYear"
                    calendar.set(selectedYear, selectedMonth + 1, selectedDay, 23, 59, 59)
                    endDate = calendar.timeInMillis

                    getEntries()
                }, year, month, day)
            datePickerDialog.show()
        }

        projectName.setOnItemClickListener { parent, view, position, _ ->
            closeKeyboard(view)

            val item = parent.getItemAtPosition(position)
            if (item is Project) {
                val project: Project = item
                this@EntryOverviewActivity.project = project
                this@EntryOverviewActivity.projectId = project.id
                this@EntryOverviewActivity.taskId = -1
                taskName.setText("")
                projectChosen = true
            }

            getEntries()
            getTasks()
        }

        projectName.setOnDismissListener {
            if (projectChosen) {
                projectChosen = false
            } else {
                projectName.setText("")
                taskName.setText("")
                this@EntryOverviewActivity.projectId = -1
                this@EntryOverviewActivity.taskId = -1
                closeKeyboard(projectName)
            }

            getEntries()
            getTasks()
        }

        projectName.setOnFocusChangeListener { view, hasFocus ->
            if (!hasFocus) {
                closeKeyboard(view)
            }
        }

        taskName.setOnItemClickListener { parent, view, position, _ ->
            closeKeyboard(view)

            val item = parent.getItemAtPosition(position)
            if (item is Task) {
                val task: Task = item
                this@EntryOverviewActivity.task = task
                this@EntryOverviewActivity.taskId = task.id
                taskChosen = true
            }

            getEntries()
        }

        taskName.setOnDismissListener {
            if (taskChosen) {
                taskChosen = false
            } else {
                taskName.setText("")
                this@EntryOverviewActivity.taskId = -1
                closeKeyboard(taskName)
            }

            getEntries()
        }

        taskName.setOnFocusChangeListener { view, hasFocus ->
            if (!hasFocus) {
                closeKeyboard(view)
            }
        }

        addButton.setOnClickListener {
            val intent = Intent(this, AddEntryActivity::class.java)
            startActivity(intent)
        }

        backButton.setOnClickListener {
            finish()
        }
    }

    override fun onResume() {
        super.onResume()

        getEntries()

        projectViewModel.getAllProjects()

        getTasks()
    }

    private fun getEntries() {
        if (taskId > 0) {
            viewModel.getEntriesForTask(startDate, endDate, taskId)
        } else if (projectId > 0) {
            viewModel.getEntriesForProject(startDate, endDate, projectId)
        } else {
            viewModel.getAllEntries(startDate, endDate)
        }
        viewModel.getTotalTime()
    }

    private fun getTasks() {
        if (projectId > 0) {
            taskViewModel.getTasksForProject(projectId)
        } else {
            taskViewModel.getAllTasks()
        }
    }

    private fun closeKeyboard(view: View) {
        val inputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.applicationWindowToken, 0)
    }

    private fun getTotalTimeText(totalTime: Long): String {
        if (totalTime > 0) {
            val seconds = totalTime / 1000
            val minutes = seconds / 60
            val minutesLeft = minutes.mod(60)
            val hours = minutes / 60
            val secondsLeft = seconds - (minutesLeft * 60) - (hours * 3600)

            return "Total time: " + hours.toString() + "h " + minutesLeft.toString() + "min " + secondsLeft.toString() + "s"
        }

        return "There are no entries to summarize"
    }
}
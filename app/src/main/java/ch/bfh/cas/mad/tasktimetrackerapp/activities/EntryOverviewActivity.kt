package ch.bfh.cas.mad.tasktimetrackerapp.activities

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ch.bfh.cas.mad.tasktimetrackerapp.csvExport.CSVExportHelper
import ch.bfh.cas.mad.tasktimetrackerapp.R
import ch.bfh.cas.mad.tasktimetrackerapp.adapter.EntryAdapter
import ch.bfh.cas.mad.tasktimetrackerapp.pdfExport.PdfExportHelper
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
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.Calendar
import java.util.Date
import java.util.Locale

class EntryOverviewActivity : ComponentActivity() {

    private lateinit var viewModel: EntryOverviewViewModel
    private lateinit var projectViewModel: ProjectOverviewViewModel
    private lateinit var taskViewModel: TaskOverviewViewModel
    private lateinit var addButton: FloatingActionButton
    private lateinit var backButton: FloatingActionButton
    private lateinit var exportButton: Button
    private lateinit var printButton: Button
    private lateinit var projectName: AutoCompleteTextView
    private lateinit var taskName: AutoCompleteTextView
    private lateinit var totalTimeView: TextView
    private lateinit var project: Project
    private lateinit var projects: List<Project>
    private lateinit var task: Task
    private lateinit var tasks: List<Task>
    private var startDate: Long = Long.MIN_VALUE
    private var endDate: Long = Long.MAX_VALUE
    private var projectId: Int = -1
    private var taskId: Int = -1
    private var projectChosen: Boolean = false
    private var taskChosen: Boolean = false

    private val dateFormat = SimpleDateFormat("yyyy-MM-dd-HH-mm-ss", Locale.getDefault())
    private val dateStr = dateFormat.format(Date())
    private val fileName = "TTT-Export-$dateStr"


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

        exportButton = findViewById(R.id.buttonExport)
        printButton = findViewById(R.id.buttonPrint)

        projectName = findViewById(R.id.projectName)
        taskName = findViewById(R.id.taskName)
        totalTimeView = findViewById(R.id.textViewTotalTime)

        // getEntriesFor...
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewEntries)
        recyclerView.layoutManager = LinearLayoutManager(this)
        lifecycleScope.launch {
            taskViewModel.tasks.collectLatest { tasks ->
                this@EntryOverviewActivity.tasks = tasks
                val taskNames = tasks.map { it.getTaskName() }
                val adapter = ArrayAdapter(this@EntryOverviewActivity, android.R.layout.simple_dropdown_item_1line, taskNames)
                taskName.setAdapter(adapter)

                // Innerhalb des Aufrufs, damit alle Daten vorhanden sind für EntryAdapter
                viewModel.entries.collectLatest { entries ->
                    val adapter = EntryAdapter(entries = entries, tasks = taskViewModel.tasks.value)
                    recyclerView.adapter = adapter
                }
            }
        }

        // getAllProjects
        lifecycleScope.launch {
            projectViewModel.projects.collectLatest { projects ->
                this@EntryOverviewActivity.projects = projects
                val projectNames = projects.map { it.getProjectName()}
                val adapter = ArrayAdapter(this@EntryOverviewActivity, android.R.layout.simple_dropdown_item_1line, projectNames)
                projectName.setAdapter(adapter)
            }
        }

        // Im Aufruf beim holen der entries für die recycler view, sonst werden die tasks nicht immer geholt!?!?
//        // getAllTasks
//        lifecycleScope.launch {
//            taskViewModel.tasks.collectLatest { tasks ->
//                val adapter = ArrayAdapter(this@EntryOverviewActivity, android.R.layout.simple_dropdown_item_1line, tasks)
//                taskName.setAdapter(adapter)
//            }
//        }

        // getTotalTime
        lifecycleScope.launch {
            viewModel.totalTime.collectLatest { totalTime ->
                totalTimeView.text = getTotalTimeText(totalTime)
            }
        }

        val textViewStartDate = findViewById<TextView>(R.id.editTextStartDate)
        val textViewEndDate = findViewById<TextView>(R.id.editTextEndDate)

        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        textViewStartDate.setOnClickListener {
            val datePickerDialog = DatePickerDialog(this,
                { _, selectedYear, selectedMonth, selectedDay ->
                    textViewStartDate.text = "$selectedDay/${selectedMonth + 1}/$selectedYear"

                    // why the hell do we have to add one month?
                    startDate = LocalDateTime.of(selectedYear, selectedMonth + 1, selectedDay, 0, 0, 0).atZone(
                        ZoneOffset.UTC).toEpochSecond()

                    getEntries()
                }, year, month, day)
            datePickerDialog.show()
        }

        textViewEndDate.setOnClickListener {
            val datePickerDialog = DatePickerDialog(this,
                { _, selectedYear, selectedMonth, selectedDay ->
                    textViewEndDate.text = "$selectedDay/${selectedMonth + 1}/$selectedYear"

                    endDate = LocalDateTime.of(selectedYear, selectedMonth + 1, selectedDay, 23, 59, 59).atZone(
                        ZoneOffset.UTC).toEpochSecond()

                    getEntries()
                }, year, month, day)
            datePickerDialog.show()
        }

        projectName.setOnItemClickListener { parent, view, position, _ ->
            closeKeyboard(view)
            val selectedProjectName = parent.getItemAtPosition(position) as String
            val selectedProject = projects.first { it.getProjectName() == selectedProjectName }
            this@EntryOverviewActivity.project = selectedProject
            this@EntryOverviewActivity.projectId = selectedProject.id
            this@EntryOverviewActivity.taskId = -1
            taskName.setText("")
            projectChosen = true
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
            val selectedTaskName = parent.getItemAtPosition(position) as String
            val selectedTask = tasks.first { it.getTaskName() == selectedTaskName }
            this@EntryOverviewActivity.task = selectedTask
            this@EntryOverviewActivity.taskId = selectedTask.taskId
            taskChosen = true
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

        //TODO: Hier Daten aufbereiten (Project / Dauer) fuer CSV - Export is Working fine
        exportButton.setOnClickListener {
            val content = viewModel.entries.value.map { entry ->
                listOf(
                    entry.id.toString(),
                    entry.description,
                    entry.taskId.toString(),
                    entry.timeStamp.toString()
                )}
            val csvExportHelper = CSVExportHelper(this)
            csvExportHelper.exportToCsv(content, "$fileName.csv")}


        printButton.setOnClickListener {
            val content = viewModel.entries.value
            val pdfExportHelper = PdfExportHelper(this)
            pdfExportHelper.createAndSavePdf("$fileName.pdf" , content, projectName.text.toString() + " - " + taskName.text.toString())
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

    private fun getTotalTimeText(totalTimeInSeconds: Long): String {
        if (totalTimeInSeconds > 0) {
            val minutes = totalTimeInSeconds / 60
            val minutesLeft = minutes.mod(60)
            val hours = minutes / 60
            val secondsLeft = totalTimeInSeconds - (minutesLeft * 60) - (hours * 3600)

            return "Total time: " + hours.toString() + "h " + minutesLeft.toString() + "min " + secondsLeft.toString() + "s"
        }

        return "There are no entries to summarize"
    }
}
package ch.bfh.cas.mad.tasktimetrackerapp.activities

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.EditText
import androidx.activity.ComponentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import ch.bfh.cas.mad.tasktimetrackerapp.R
import ch.bfh.cas.mad.tasktimetrackerapp.persistence.Project
import ch.bfh.cas.mad.tasktimetrackerapp.persistence.ProjectRepository
import ch.bfh.cas.mad.tasktimetrackerapp.persistence.TTTDatabaseProvider
import ch.bfh.cas.mad.tasktimetrackerapp.persistence.Task
import ch.bfh.cas.mad.tasktimetrackerapp.persistence.TaskRepository
import ch.bfh.cas.mad.tasktimetrackerapp.viewModel.AddTaskViewModel
import ch.bfh.cas.mad.tasktimetrackerapp.viewModel.AddTaskViewModelFactory
import ch.bfh.cas.mad.tasktimetrackerapp.viewModel.ProjectOverviewViewModel
import ch.bfh.cas.mad.tasktimetrackerapp.viewModel.ProjectOverviewViewModelFactory
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch


class AddTaskActivity : ComponentActivity() {

    private lateinit var viewModel: AddTaskViewModel
    private lateinit var projectViewModel: ProjectOverviewViewModel
    private lateinit var addTaskButton: Button
    private lateinit var projectName: AutoCompleteTextView
    private lateinit var backButton: FloatingActionButton
    private lateinit var project: Project

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_addtask)

        val viewModelProvider = ViewModelProvider(
            this,
            AddTaskViewModelFactory(TaskRepository(TTTDatabaseProvider.get(this).getTaskDao(), TTTDatabaseProvider.get(this).getEntryDao()))
        )

        val projectViewModelProvider = ViewModelProvider(
            this,
            ProjectOverviewViewModelFactory(ProjectRepository(TTTDatabaseProvider.get(this).getProjectDao(), TTTDatabaseProvider.get(this).getEntryDao()))
        )

        viewModel = viewModelProvider[AddTaskViewModel::class.java]
        projectViewModel = projectViewModelProvider[ProjectOverviewViewModel::class.java]

        addTaskButton = findViewById(R.id.addTaskButton)
        backButton = findViewById(R.id.fabBack)

        projectName = findViewById(R.id.projectName)

        // Variant 1, have a list of project names, which is correct for dropdown but not feasable for getting project object
        // getAllProjects
//        lifecycleScope.launch {
//            projectViewModel.projects.collectLatest { projects ->
//                val projectNames = projects.map {it.name }
//                val adapter = ArrayAdapter(this@AddTaskActivity, android.R.layout.simple_dropdown_item_1line, projectNames)
//                projectName.setAdapter(adapter)
//
//                projectName.setOnItemClickListener{ _, _, position, _ ->
//                    val item = projects[position]
//                    this@AddTaskActivity.project = item
//                }
//            }
//        }

        // Variant 2, have a list of Project which is better to get item but not shown pretty in dropdown
        // getAllProjects
        lifecycleScope.launch {
            projectViewModel.projects.collectLatest { projects ->
                val adapter = ArrayAdapter(this@AddTaskActivity, android.R.layout.simple_dropdown_item_1line, projects)
                projectName.setAdapter(adapter)
            }
        }

        // Variant 2, have a list of Project which is better to get item but not shown pretty in dropdown
        projectName.setOnItemClickListener{ parent, _, position, _ ->
            val item = parent.getItemAtPosition(position)
            if (item is Project) {
                val project: Project = item
                this@AddTaskActivity.project = project
            }
        }

        addTaskButton.setOnClickListener {
            val taskName = findViewById<EditText>(R.id.taskName).text.toString()    // ToDo: check for empty string, then try to to have a default value from entity
            val task = Task(0, taskName, project.id)   // id for task is autoGenerated
            viewModel.addTask(task)
            finish()
        }

        backButton.setOnClickListener {
            finish()
        }
    }

    override fun onResume() {
        super.onResume()
        projectViewModel.getAllProjects()
    }
}
package ch.bfh.cas.mad.tasktimetrackerapp.activities

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ch.bfh.cas.mad.tasktimetrackerapp.adapter.ProjectAdapter
import ch.bfh.cas.mad.tasktimetrackerapp.R
import ch.bfh.cas.mad.tasktimetrackerapp.persistence.Project
import ch.bfh.cas.mad.tasktimetrackerapp.persistence.ProjectRepository
import ch.bfh.cas.mad.tasktimetrackerapp.persistence.TTTDatabaseProvider
import ch.bfh.cas.mad.tasktimetrackerapp.viewModel.ProjectOverviewViewModel
import ch.bfh.cas.mad.tasktimetrackerapp.viewModel.ProjectOverviewViewModelFactory
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class ProjectOverviewActivity : ComponentActivity() {

    private lateinit var viewModel: ProjectOverviewViewModel
    private lateinit var addProjectButton: FloatingActionButton
    private lateinit var backButton: FloatingActionButton

    private var projects: MutableList<Project> = mutableListOf(Project(id = 1, name = "Initial Project"))
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_projectoverview)
        val viewModelProvider = ViewModelProvider(
            this,
            ProjectOverviewViewModelFactory(ProjectRepository(TTTDatabaseProvider.get(this).getProjectDao()))
        )
        viewModel = viewModelProvider[ProjectOverviewViewModel::class.java]
        addProjectButton = findViewById(R.id.AddProject)
        backButton = findViewById(R.id.fabBack)
        val recyclerViewProjects = findViewById<RecyclerView>(R.id.recyclerViewProjects)

        recyclerViewProjects.layoutManager = LinearLayoutManager(this)
        lifecycleScope.launch {
            viewModel.projects.collectLatest { projects ->
                val adapter = ProjectAdapter(projects = projects)
                recyclerViewProjects.adapter = adapter
            }
        }
        // ToDo: delete only for tests/initializing
        //viewModel.deleteAllProjects()
        viewModel.getAllProjects()

        addProjectButton.setOnClickListener {
            val intent = Intent(this, AddProjectActivity::class.java)
            startActivity(intent)
        }

        backButton.setOnClickListener {
            finish()
        }
    }
}
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
import ch.bfh.cas.mad.tasktimetrackerapp.viewModel.ProjectDetailViewModel
import ch.bfh.cas.mad.tasktimetrackerapp.viewModel.ProjectDetailViewModelFactory
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class DetailProjectActivity : ComponentActivity() {

    private lateinit var viewModel: ProjectDetailViewModel
    private lateinit var showAllEntriesButton: Button
    private lateinit var backButton: FloatingActionButton
    private lateinit var projectNameView: TextView
    private var projectId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detailproject)

        val viewModelProvider = ViewModelProvider(
            this,
            ProjectDetailViewModelFactory(ProjectRepository(TTTDatabaseProvider.get(this).getProjectDao(), TTTDatabaseProvider.get(this).getEntryDao()))
        )

        viewModel = viewModelProvider[ProjectDetailViewModel::class.java]

        showAllEntriesButton = findViewById(R.id.btnShowAllEntries)
        backButton = findViewById(R.id.fabBack)
        projectNameView = findViewById(R.id.TextViewProjectName)

        projectId = intent.getIntExtra("projectId", -1)

        val recyclerView = findViewById<RecyclerView>(R.id.entriesRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        lifecycleScope.launch {
            viewModel.entries.collectLatest { entries ->
                val adapter = EntryAdapter(entries = entries)
                recyclerView.adapter = adapter

                viewModel.projectName.collectLatest { projectName ->
                    projectNameView.text = projectName
                }
            }
        }

        showAllEntriesButton.setOnClickListener {
            val intent = Intent(this, EntriesOverviewActivity::class.java)
            intent.putExtra("projectId", projectId)
            startActivity(intent)
        }

        backButton.setOnClickListener {
            finish()
        }
    }

    override fun onResume() {
        super.onResume()
        // ToDo: think about merging all together into one single method e.g. "viewModel.ReadProjectData()"
        viewModel.getEntriesForProject(projectId)
        viewModel.getProjectName(projectId)
    }
}
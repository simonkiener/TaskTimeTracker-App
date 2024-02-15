package ch.bfh.cas.mad.tasktimetrackerapp.activities

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.ComponentActivity
import androidx.lifecycle.ViewModelProvider
import ch.bfh.cas.mad.tasktimetrackerapp.persistence.Project
import ch.bfh.cas.mad.tasktimetrackerapp.R
import ch.bfh.cas.mad.tasktimetrackerapp.persistence.ProjectRepository
import ch.bfh.cas.mad.tasktimetrackerapp.persistence.TTTDatabaseProvider
import ch.bfh.cas.mad.tasktimetrackerapp.viewModel.AddProjectViewModel
import ch.bfh.cas.mad.tasktimetrackerapp.viewModel.AddProjectViewModelFactory
import com.google.android.material.floatingactionbutton.FloatingActionButton

class AddProjectActivity : ComponentActivity() {

    private lateinit var viewModel: AddProjectViewModel
    private lateinit var addProjectButton: Button
    private lateinit var backButton: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_addproject)
        val viewModelProvider = ViewModelProvider(
            this,
            AddProjectViewModelFactory(ProjectRepository(TTTDatabaseProvider.get(this).getProjectDao()))
        )
        viewModel = viewModelProvider[AddProjectViewModel::class.java]
        addProjectButton = findViewById(R.id.addButton)
        backButton = findViewById(R.id.fabBack)

        addProjectButton.setOnClickListener {
            val projectName = findViewById<EditText>(R.id.ProjectName).text.toString()
            val project = Project(projectName, 0)   // id is autoGenerated
            viewModel.addProject(project)
            finish()
        }

        backButton.setOnClickListener {
            finish()
        }
    }
}
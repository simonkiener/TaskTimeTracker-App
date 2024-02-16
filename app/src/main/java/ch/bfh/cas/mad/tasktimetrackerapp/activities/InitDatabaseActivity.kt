package ch.bfh.cas.mad.tasktimetrackerapp.activities

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.ComponentActivity
import androidx.lifecycle.ViewModelProvider
import ch.bfh.cas.mad.tasktimetrackerapp.persistence.Project
import ch.bfh.cas.mad.tasktimetrackerapp.R
import ch.bfh.cas.mad.tasktimetrackerapp.persistence.DatabaseRepository
import ch.bfh.cas.mad.tasktimetrackerapp.persistence.ProjectRepository
import ch.bfh.cas.mad.tasktimetrackerapp.persistence.TTTDatabaseProvider
import ch.bfh.cas.mad.tasktimetrackerapp.viewModel.AddProjectViewModel
import ch.bfh.cas.mad.tasktimetrackerapp.viewModel.AddProjectViewModelFactory
import ch.bfh.cas.mad.tasktimetrackerapp.viewModel.InitDatabaseViewModel
import ch.bfh.cas.mad.tasktimetrackerapp.viewModel.InitDatabaseViewModelFactory
import com.google.android.material.floatingactionbutton.FloatingActionButton

class InitDatabaseActivity : ComponentActivity() {

    private lateinit var viewModel: InitDatabaseViewModel
    private lateinit var initDatabaseButton: Button
    private lateinit var backButton: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_initdatabase)

        val viewModelProvider = ViewModelProvider(
            this,
            InitDatabaseViewModelFactory(DatabaseRepository(TTTDatabaseProvider.get(this).getProjectDao(), TTTDatabaseProvider.get(this).getTaskDao(),TTTDatabaseProvider.get(this).getEntryDao()))
        )

        viewModel = viewModelProvider[InitDatabaseViewModel::class.java]

        initDatabaseButton = findViewById(R.id.initDatabaseButton)
        backButton = findViewById(R.id.fabBack)

        initDatabaseButton.setOnClickListener {
            viewModel.initDatabase()
            finish()
        }

        backButton.setOnClickListener {
            finish()
        }
    }
}
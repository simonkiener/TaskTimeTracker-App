package ch.bfh.cas.mad.tasktimetrackerapp.activities

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import ch.bfh.cas.mad.tasktimetrackerapp.DataStore
import ch.bfh.cas.mad.tasktimetrackerapp.Project
import ch.bfh.cas.mad.tasktimetrackerapp.R
import ch.bfh.cas.mad.tasktimetrackerapp.ui.theme.TaskTimeTrackerAppTheme
import com.google.android.material.floatingactionbutton.FloatingActionButton

class AddProjectActivity : ComponentActivity() {

    private lateinit var addProjectButton: Button
    private lateinit var backButton: FloatingActionButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_addproject)

        addProjectButton = findViewById(R.id.addButton)
        backButton = findViewById(R.id.fabBack)

        addProjectButton.setOnClickListener {
            val projectName = findViewById<EditText>(R.id.ProjectName).text.toString()
            val project = Project(projectName, DataStore.projects.size + 1)
            DataStore.projects.add(project)
            finish()
        }

        backButton.setOnClickListener {
            finish()
        }
    }
}
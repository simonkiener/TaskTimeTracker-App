package ch.bfh.cas.mad.tasktimetrackerapp.activities

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import ch.bfh.cas.mad.tasktimetrackerapp.R
import ch.bfh.cas.mad.tasktimetrackerapp.ui.theme.TaskTimeTrackerAppTheme

class AddTaskActivity : ComponentActivity() {

    private lateinit var projectName: AutoCompleteTextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_addtask)

        projectName = findViewById(R.id.projectName)

        val projects = arrayOf("Projekt 1", "Projekt 2", "Projekt 3")
        val adapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, projects)
        projectName.setAdapter(adapter)
    }
}
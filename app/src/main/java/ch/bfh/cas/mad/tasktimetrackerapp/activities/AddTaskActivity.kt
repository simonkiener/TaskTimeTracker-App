package ch.bfh.cas.mad.tasktimetrackerapp.activities

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.activity.ComponentActivity
import ch.bfh.cas.mad.tasktimetrackerapp.persistence.DataStore
import ch.bfh.cas.mad.tasktimetrackerapp.R
import ch.bfh.cas.mad.tasktimetrackerapp.ui.theme.TaskTimeTrackerAppTheme
import com.google.android.material.floatingactionbutton.FloatingActionButton

class AddTaskActivity : ComponentActivity() {

    private lateinit var projectName: AutoCompleteTextView
    private lateinit var backButton: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_addtask)

        backButton = findViewById(R.id.fabBack)

        projectName = findViewById(R.id.projectName)

        val projects = DataStore.projects.map {it.name }
        val adapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, projects)
        projectName.setAdapter(adapter)

        backButton.setOnClickListener {
            finish()
        }
    }
}
package ch.bfh.cas.mad.tasktimetrackerapp.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import ch.bfh.cas.mad.tasktimetrackerapp.R
import ch.bfh.cas.mad.tasktimetrackerapp.ui.theme.TaskTimeTrackerAppTheme

class MainActivity : ComponentActivity() {
    private lateinit var taskNavigationButton: Button
    private lateinit var projectNavigationButton: Button
    private lateinit var entriesNavigationButton: Button
    private lateinit var databaseInitButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        taskNavigationButton = findViewById(R.id.main_buttonTasks)
        projectNavigationButton = findViewById(R.id.main_buttonProjects)
        entriesNavigationButton = findViewById(R.id.main_buttonEntries)
        databaseInitButton = findViewById(R.id.main_buttonDatabaseInit)

        taskNavigationButton.setOnClickListener {
            val intent = Intent(this, TaskOverviewActivity::class.java)
            startActivity(intent)
        }

        projectNavigationButton.setOnClickListener {
            val intent = Intent(this, ProjectOverviewActivity::class.java)
            startActivity(intent)
        }

        entriesNavigationButton.setOnClickListener {
            val intent = Intent(this, EntriesOverviewActivity::class.java)
            startActivity(intent)
        }

        databaseInitButton.setOnClickListener {
            val intent = Intent(this, InitDatabaseActivity::class.java)
            startActivity(intent)
        }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TaskTimeTrackerAppTheme {
    }
}
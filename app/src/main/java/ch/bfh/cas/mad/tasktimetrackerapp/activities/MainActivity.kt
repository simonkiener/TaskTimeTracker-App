package ch.bfh.cas.mad.tasktimetrackerapp.activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import ch.bfh.cas.mad.tasktimetrackerapp.R
import ch.bfh.cas.mad.tasktimetrackerapp.ui.theme.TaskTimeTrackerAppTheme
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : ComponentActivity() {
    private lateinit var taskNavigationButton: Button
    private lateinit var projectNavigationButton: Button
    private lateinit var entriesNavigationButton: Button
    private lateinit var databaseInitButton: Button

    private lateinit var taskAssignmentButton: FloatingActionButton
    private lateinit var WidgetSpot1: Button
    private lateinit var WidgetSpot2: Button
    private lateinit var WidgetSpot3: Button
    private lateinit var WidgetSpot4: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        taskNavigationButton = findViewById(R.id.main_buttonTasks)
        projectNavigationButton = findViewById(R.id.main_buttonProjects)
        entriesNavigationButton = findViewById(R.id.main_buttonEntries)
        databaseInitButton = findViewById(R.id.main_buttonDatabaseInit)
        taskAssignmentButton = findViewById(R.id.TaskAssingmentButton)
        WidgetSpot1 = findViewById(R.id.main_buttonTask2)
        WidgetSpot2 = findViewById(R.id.main_buttonTask1)
        WidgetSpot3 = findViewById(R.id.main_buttonTask4)
        WidgetSpot4 = findViewById(R.id.main_buttonTask3)

        val sharedPreferences: SharedPreferences = getSharedPreferences("selectedTasks", Context.MODE_PRIVATE)
        val selectedTasks = sharedPreferences.getStringSet("selectedTasks", emptySet())

        val widgetButtons = listOf(WidgetSpot1, WidgetSpot2, WidgetSpot3, WidgetSpot4)

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
        taskAssignmentButton.setOnClickListener {
            val intent = Intent(this, WidgetTaskSettingActivity::class.java)
            startActivity(intent)
        }

        //Change the background of the buttons if Task is selected to recording
        widgetButtons.forEach { button ->
            button.setOnClickListener {
                widgetButtons.forEach { it.setBackgroundResource(R.drawable.round_button_inactiv) }
                button.setBackgroundResource(R.drawable.round_button_activ)
            }
        }

    }

    override fun onResume() {
        super.onResume()

        val sharedPreferences: SharedPreferences = getSharedPreferences("selectedTasks", Context.MODE_PRIVATE)
        val selectedTasks = sharedPreferences.getStringSet("selectedTasks", emptySet())

        // Setzen Sie den Text der Buttons auf die ausgewählten Tasks
        WidgetSpot1.text = selectedTasks?.elementAtOrNull(0) ?: "Select Task in App"
        WidgetSpot2.text = selectedTasks?.elementAtOrNull(1) ?: "Select Task in App"
        WidgetSpot3.text = selectedTasks?.elementAtOrNull(2) ?: "Select Task in App"
        WidgetSpot4.text = selectedTasks?.elementAtOrNull(3) ?: "Select Task in App"

    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    TaskTimeTrackerAppTheme {
    }
}
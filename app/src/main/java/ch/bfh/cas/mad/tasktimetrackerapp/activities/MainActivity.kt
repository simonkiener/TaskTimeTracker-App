package ch.bfh.cas.mad.tasktimetrackerapp.activities

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import ch.bfh.cas.mad.tasktimetrackerapp.R
import ch.bfh.cas.mad.tasktimetrackerapp.ui.theme.TaskTimeTrackerAppTheme
import ch.bfh.cas.mad.tasktimetrackerapp.widget.BroadcastReceiver
import ch.bfh.cas.mad.tasktimetrackerapp.widget.WidgetProvider.Companion.ACTION_WIDGET_BUTTON_1
import ch.bfh.cas.mad.tasktimetrackerapp.widget.WidgetProvider.Companion.ACTION_WIDGET_BUTTON_2
import ch.bfh.cas.mad.tasktimetrackerapp.widget.WidgetProvider.Companion.ACTION_WIDGET_BUTTON_3
import ch.bfh.cas.mad.tasktimetrackerapp.widget.WidgetProvider.Companion.ACTION_WIDGET_BUTTON_4
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

    private val receiver = BroadcastReceiver()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        taskNavigationButton = findViewById(R.id.main_buttonTasks)
        projectNavigationButton = findViewById(R.id.main_buttonProjects)
        entriesNavigationButton = findViewById(R.id.main_buttonEntries)
        databaseInitButton = findViewById(R.id.main_buttonDatabaseInit)
        taskAssignmentButton = findViewById(R.id.TaskAssingmentButton)
        WidgetSpot1 = findViewById(R.id.main_buttonTask1)
        WidgetSpot2 = findViewById(R.id.main_buttonTask2)
        WidgetSpot3 = findViewById(R.id.main_buttonTask3)
        WidgetSpot4 = findViewById(R.id.main_buttonTask4)

        val sharedPreferences: SharedPreferences = getSharedPreferences("selectedTasks", Context.MODE_PRIVATE)

        val widgetButtons = listOf(WidgetSpot1, WidgetSpot2, WidgetSpot3, WidgetSpot4)

        val filter = IntentFilter()
        filter.addAction(ACTION_WIDGET_BUTTON_1)
        filter.addAction(ACTION_WIDGET_BUTTON_2)
        filter.addAction(ACTION_WIDGET_BUTTON_3)
        filter.addAction(ACTION_WIDGET_BUTTON_4)
        registerReceiver(receiver, filter, RECEIVER_EXPORTED)

        taskNavigationButton.setOnClickListener {
            val intent = Intent(this, TaskOverviewActivity::class.java)
            startActivity(intent)
        }

        projectNavigationButton.setOnClickListener {
            val intent = Intent(this, ProjectOverviewActivity::class.java)
            startActivity(intent)
        }

        entriesNavigationButton.setOnClickListener {
            val intent = Intent(this, EntryOverviewActivity::class.java)
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

        val sharedPreferences: SharedPreferences =
            getSharedPreferences("selectedTasks", Context.MODE_PRIVATE)
        val selectedTask1 = sharedPreferences.getString("selectedTask1", "")
        val selectedTask2 = sharedPreferences.getString("selectedTask2", "")
        val selectedTask3 = sharedPreferences.getString("selectedTask3", "")
        val selectedTask4 = sharedPreferences.getString("selectedTask4", "")

        // Setzen Sie den Text der Buttons auf die ausgewählten Tasks
        WidgetSpot1.text = selectedTask1 ?: "Select Task in App"
        WidgetSpot2.text = selectedTask2 ?: "Select Task in App"
        WidgetSpot3.text = selectedTask3 ?: "Select Task in App"
        WidgetSpot4.text = selectedTask4 ?: "Select Task in App"
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(receiver)
    }


    @Preview(showBackground = true)
    @Composable
    fun GreetingPreview() {
        TaskTimeTrackerAppTheme {
        }
    }
}
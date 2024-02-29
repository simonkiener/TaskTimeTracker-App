package ch.bfh.cas.mad.tasktimetrackerapp.activities

import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.RemoteViews
import androidx.activity.ComponentActivity
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import ch.bfh.cas.mad.tasktimetrackerapp.R
import ch.bfh.cas.mad.tasktimetrackerapp.persistence.TTTDatabaseProvider
import ch.bfh.cas.mad.tasktimetrackerapp.persistence.WidgetTask
import ch.bfh.cas.mad.tasktimetrackerapp.persistence.WidgetTaskRepository
import ch.bfh.cas.mad.tasktimetrackerapp.ui.theme.TaskTimeTrackerAppTheme
import ch.bfh.cas.mad.tasktimetrackerapp.viewModel.MainViewModel
import ch.bfh.cas.mad.tasktimetrackerapp.viewModel.MainViewModelFactory
import ch.bfh.cas.mad.tasktimetrackerapp.widget.BroadcastReceiver
import ch.bfh.cas.mad.tasktimetrackerapp.widget.WidgetProvider
import ch.bfh.cas.mad.tasktimetrackerapp.widget.WidgetProvider.Companion.ACTION_WIDGET_BUTTON_1
import ch.bfh.cas.mad.tasktimetrackerapp.widget.WidgetProvider.Companion.ACTION_WIDGET_BUTTON_2
import ch.bfh.cas.mad.tasktimetrackerapp.widget.WidgetProvider.Companion.ACTION_WIDGET_BUTTON_3
import ch.bfh.cas.mad.tasktimetrackerapp.widget.WidgetProvider.Companion.ACTION_WIDGET_BUTTON_4
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var widgetTasks: List<WidgetTask>
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

        val viewModelProvider = ViewModelProvider(
            this,
            MainViewModelFactory(WidgetTaskRepository(TTTDatabaseProvider.get(this).getWidgetTaskDao()))
        )

        viewModel = viewModelProvider[MainViewModel::class.java]

        taskNavigationButton = findViewById(R.id.main_buttonTasks)
        projectNavigationButton = findViewById(R.id.main_buttonProjects)
        entriesNavigationButton = findViewById(R.id.main_buttonEntries)
        databaseInitButton = findViewById(R.id.main_buttonDatabaseInit)
        taskAssignmentButton = findViewById(R.id.TaskAssingmentButton)
        WidgetSpot1 = findViewById(R.id.main_buttonTask1)
        WidgetSpot2 = findViewById(R.id.main_buttonTask2)
        WidgetSpot3 = findViewById(R.id.main_buttonTask3)
        WidgetSpot4 = findViewById(R.id.main_buttonTask4)

        // getAllWidgetTasks
        lifecycleScope.launch {
            viewModel.widgetTasks.collectLatest { widgetTasks ->
                this@MainActivity.widgetTasks = widgetTasks
            }
        }

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
                val isActive = button.isActivated
                widgetButtons.forEach {
                    it.setBackgroundResource(R.drawable.round_button_inactiv)
                    it.isActivated = false
                    updateWidgetViews(
                        RemoteViews(packageName, R.layout.widget_layout),
                        0
                    )
                }

                if (!isActive) {
                    button.setBackgroundResource(R.drawable.round_button_activ)
                    button.isActivated = true
                    updateWidgetViews(
                        RemoteViews(packageName, R.layout.widget_layout),
                        widgetButtons.indexOf(it) + 1
                    )
                }

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

    private fun updateWidgetViews(views: RemoteViews, buttonNumber: Int) {
        val buttons = listOf(R.id.widget_button1, R.id.widget_button2, R.id.widget_button3, R.id.widget_button4)
        buttons.forEachIndexed { index, buttonId ->
            views.setInt(buttonId, "setBackgroundResource", if (index + 1 == buttonNumber) R.drawable.round_button_activ else R.drawable.round_button_inactiv)

            // Get the AppWidgetManager instance
            val appWidgetManager = AppWidgetManager.getInstance(this)

            // Get the widget ids for your widget
            val ids = appWidgetManager.getAppWidgetIds(ComponentName(this, WidgetProvider::class.java))

            // Update the widget
            ids.forEach { id ->
                appWidgetManager.updateAppWidget(id, views)
            }
        }
    }


    @Preview(showBackground = true)
    @Composable
    fun GreetingPreview() {
        TaskTimeTrackerAppTheme {
        }
    }
}
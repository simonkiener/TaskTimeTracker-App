package ch.bfh.cas.mad.tasktimetrackerapp.activities

import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.RemoteViews
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import ch.bfh.cas.mad.tasktimetrackerapp.R
import ch.bfh.cas.mad.tasktimetrackerapp.persistence.EntryRepository
import ch.bfh.cas.mad.tasktimetrackerapp.persistence.TTTDatabaseProvider
import ch.bfh.cas.mad.tasktimetrackerapp.persistence.WidgetTask
import ch.bfh.cas.mad.tasktimetrackerapp.persistence.WidgetTaskRepository
import ch.bfh.cas.mad.tasktimetrackerapp.ui.theme.TaskTimeTrackerAppTheme
import ch.bfh.cas.mad.tasktimetrackerapp.viewModel.MainViewModel
import ch.bfh.cas.mad.tasktimetrackerapp.viewModel.MainViewModelFactory
import ch.bfh.cas.mad.tasktimetrackerapp.widget.BroadcastReceiver
import ch.bfh.cas.mad.tasktimetrackerapp.widget.WidgetProvider
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.viewModelScope
import ch.bfh.cas.mad.tasktimetrackerapp.persistence.Entry


class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainViewModel
    private lateinit var widgetTasks: List<WidgetTask>
    private val noOfWidgetTasks = 4

    private lateinit var taskAssignmentButton: FloatingActionButton
    private lateinit var widgetSpot1: Button
    private lateinit var widgetSpot2: Button
    private lateinit var widgetSpot3: Button
    private lateinit var widgetSpot4: Button
    private var widgetButtons: List<Button> = listOf()

    private val localReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            when (intent.action) {
                "ACTION_WIDGET_BUTTON_1_RECEIVED" -> {
                    // Call updateWidgetViews
                    val views = RemoteViews(packageName, R.layout.widget_layout)
                    viewModel.addEntryForWidget(1)
                    updateWidgetViews(views, 1)
                    if(widgetSpot1.isActivated)
                        updateButtonState(widgetSpot1, false)
                    else
                        updateButtonState(widgetSpot1, true)
                }
                "ACTION_WIDGET_BUTTON_2_RECEIVED" -> {
                    // Call updateWidgetViews
                    val views = RemoteViews(packageName, R.layout.widget_layout)
                    viewModel.addEntryForWidget(2)
                    updateWidgetViews(views, 2)
                    if(widgetSpot2.isActivated)
                        updateButtonState(widgetSpot2, false)
                    else
                        updateButtonState(widgetSpot2, true)
                }
                "ACTION_WIDGET_BUTTON_3_RECEIVED" -> {
                    // Call updateWidgetViews
                    val views = RemoteViews(packageName, R.layout.widget_layout)
                    viewModel.addEntryForWidget(3)
                    updateWidgetViews(views, 3)
                    if(widgetSpot3.isActivated)
                        updateButtonState(widgetSpot3, false)
                    else
                        updateButtonState(widgetSpot3, true)
                }
                "ACTION_WIDGET_BUTTON_4_RECEIVED" -> {
                    // Call updateWidgetViews
                    val views = RemoteViews(packageName, R.layout.widget_layout)
                    viewModel.addEntryForWidget(4)
                    updateWidgetViews(views, 4)
                    if(widgetSpot4.isActivated)
                        updateButtonState(widgetSpot4, false)
                    else
                        updateButtonState(widgetSpot4, true)
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        println("onCreate MainActivity")
        setContentView(R.layout.activity_main)

        LocalBroadcastManager.getInstance(this).registerReceiver(
        localReceiver,
        IntentFilter("ACTION_WIDGET_BUTTON_1_RECEIVED")
        )
        LocalBroadcastManager.getInstance(this).registerReceiver(
        localReceiver,
        IntentFilter("ACTION_WIDGET_BUTTON_2_RECEIVED")
        )
        LocalBroadcastManager.getInstance(this).registerReceiver(
        localReceiver,
        IntentFilter("ACTION_WIDGET_BUTTON_3_RECEIVED")
        )
        LocalBroadcastManager.getInstance(this).registerReceiver(
        localReceiver,
        IntentFilter("ACTION_WIDGET_BUTTON_4_RECEIVED")
        )

        val viewModelProvider = ViewModelProvider(
            this,
            MainViewModelFactory(WidgetTaskRepository(TTTDatabaseProvider.get(this).getWidgetTaskDao()), EntryRepository(TTTDatabaseProvider.get(this).getEntryDao()))
        )

        viewModel = viewModelProvider[MainViewModel::class.java]

        taskAssignmentButton = findViewById(R.id.TaskAssingmentButton)
        widgetSpot1 = findViewById(R.id.main_buttonTask1)
        widgetSpot2 = findViewById(R.id.main_buttonTask2)
        widgetSpot3 = findViewById(R.id.main_buttonTask3)
        widgetSpot4 = findViewById(R.id.main_buttonTask4)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.title = ""

        widgetButtons = listOf(widgetSpot1, widgetSpot2, widgetSpot3, widgetSpot4)

        // getAllWidgetTasks
        lifecycleScope.launch {
            viewModel.widgetTasks.collectLatest { widgetTasks ->
                this@MainActivity.widgetTasks = widgetTasks
            }
        }

        taskAssignmentButton.setOnClickListener {
            val intent = Intent(this, WidgetTaskSettingActivity::class.java)
            startActivity(intent)
        }

        //Change the background of the buttons if Task is selected to recording
        widgetButtons.forEach { button ->
            button.setOnClickListener {
                val isActive = button.isActivated
                viewModel.addEntryForWidget(widgetButtons.indexOf(button) + 1)
                widgetButtons.forEach {
                    updateButtonState(it, false)
                    updateWidgetViews(
                        RemoteViews(packageName, R.layout.widget_layout),
                        0)
                }
                if (!isActive) {
                    updateButtonState(button, true)
                    updateWidgetViews(
                        RemoteViews(packageName, R.layout.widget_layout),
                        widgetButtons.indexOf(button) + 1)
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_tasks -> {
                val intent = Intent(this, TaskOverviewActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.menu_projects -> {
                val intent = Intent(this, ProjectOverviewActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.menu_entries -> {
                val intent = Intent(this, EntryOverviewActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.menu_initDataBase -> {
                val intent = Intent(this, InitDatabaseActivity::class.java)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onResume() {
        super.onResume()
        println("onResume MainActivity")
        viewModel.getAllWidgetTasks()

        // Update WidgetTask texts
        updateWidgetTaskTexts()

        //update WidgetText
        updateWidgetViews(RemoteViews(packageName, R.layout.widget_layout),0)
    }

    override fun onDestroy() {
        super.onDestroy()
        LocalBroadcastManager.getInstance(this).unregisterReceiver(localReceiver)
    }

    private fun updateWidgetTaskTexts() {
        val noTask = "No Task"
        var index = 0

        while (index < noOfWidgetTasks) {
            val currentWidgetTask = widgetTasks.elementAtOrNull(index)
            if (currentWidgetTask != null) {
                widgetButtons[index].text = currentWidgetTask.taskId.toString()
            } else {
                widgetButtons[index].text = noTask
            }
            index++
        }
    }

    private fun updateWidgetName(views: RemoteViews){
        val buttons = listOf(R.id.widget_button1, R.id.widget_button2, R.id.widget_button3, R.id.widget_button4)
        buttons.forEachIndexed { index, buttonId ->
            views.setTextViewText(buttonId, widgetButtons[index].text)
        }
    }

    private fun updateWidgetViews(views: RemoteViews, buttonNumber: Int) {
        val buttons = listOf(R.id.widget_button1, R.id.widget_button2, R.id.widget_button3, R.id.widget_button4)
        buttons.forEachIndexed { index, buttonId ->
            views.setInt(buttonId, "setBackgroundResource", if (index + 1 == buttonNumber) R.drawable.round_button_activ else R.drawable.round_button_inactiv)
            views.setTextViewText(buttonId, widgetButtons[index].text)

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

    private fun updateButtonState(button: Button, isActive: Boolean) {
        if (isActive) {
            // First set all buttons to inactive
            widgetButtons.forEach {
                it.setBackgroundResource(R.drawable.round_button_inactiv)
                it.isActivated = false
                updateWidgetViews(
                    RemoteViews(packageName, R.layout.widget_layout),
                    0
                )
            }
        }
        if (!isActive) {
            button.setBackgroundResource(R.drawable.round_button_inactiv)
            button.isActivated = false
            updateWidgetViews(
                RemoteViews(packageName, R.layout.widget_layout),
                0
            )
        } else {
            button.setBackgroundResource(R.drawable.round_button_activ)
            button.isActivated = true
            updateWidgetViews(
                RemoteViews(packageName, R.layout.widget_layout),
                widgetButtons.indexOf(button) + 1
            )
        }
    }

    @Preview(showBackground = true)
    @Composable
    fun GreetingPreview() {
        TaskTimeTrackerAppTheme {
        }
    }
}
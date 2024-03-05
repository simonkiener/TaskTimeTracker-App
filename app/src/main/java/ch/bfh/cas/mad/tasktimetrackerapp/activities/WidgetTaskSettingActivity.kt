package ch.bfh.cas.mad.tasktimetrackerapp.activities

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import ch.bfh.cas.mad.tasktimetrackerapp.R
import ch.bfh.cas.mad.tasktimetrackerapp.persistence.TTTDatabaseProvider
import ch.bfh.cas.mad.tasktimetrackerapp.persistence.Task
import ch.bfh.cas.mad.tasktimetrackerapp.persistence.TaskRepository
import ch.bfh.cas.mad.tasktimetrackerapp.persistence.WidgetTaskRepository
import ch.bfh.cas.mad.tasktimetrackerapp.viewModel.TaskOverviewViewModel
import ch.bfh.cas.mad.tasktimetrackerapp.viewModel.TaskOverviewViewModelFactory
import ch.bfh.cas.mad.tasktimetrackerapp.viewModel.WidgetTaskSettingViewModel
import ch.bfh.cas.mad.tasktimetrackerapp.viewModel.WidgetTaskSettingViewModelFactory
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class WidgetTaskSettingActivity : ComponentActivity() {

    private lateinit var viewModel: WidgetTaskSettingViewModel
    private lateinit var taskViewModel: TaskOverviewViewModel
    private lateinit var backButton: FloatingActionButton
    private lateinit var fieldSpot1: AutoCompleteTextView
    private lateinit var fieldSpot2: AutoCompleteTextView
    private lateinit var fieldSpot3: AutoCompleteTextView
    private lateinit var fieldSpot4: AutoCompleteTextView
    private lateinit var tasks: List<Task>
    private lateinit var clearAllButton: Button
    private lateinit var addNewTaskButton: Button

    // ToDo: has some issues refreshing fieldSpot views. Especially doesn't work anymore after clear all...
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_widgettasksetting)

        val viewModelProvider = ViewModelProvider(
            this,
            WidgetTaskSettingViewModelFactory(WidgetTaskRepository(TTTDatabaseProvider.get(this).getWidgetTaskDao()))
        )

        val taskViewModelProvider = ViewModelProvider(
            this,
            TaskOverviewViewModelFactory(TaskRepository(TTTDatabaseProvider.get(this).getTaskDao(), TTTDatabaseProvider.get(this).getEntryDao()))
        )

        viewModel = viewModelProvider[WidgetTaskSettingViewModel::class.java]
        taskViewModel = taskViewModelProvider[TaskOverviewViewModel::class.java]

        backButton = findViewById(R.id.fabBack)
        clearAllButton = findViewById(R.id.buttonClearAll)
        addNewTaskButton = findViewById(R.id.AddNewTask)

        fieldSpot1 = findViewById(R.id.fieldSelectSpot1)
        fieldSpot2 = findViewById(R.id.fieldSelectSpot2)
        fieldSpot3 = findViewById(R.id.fieldSelectSpot3)
        fieldSpot4 = findViewById(R.id.fieldSelectSpot4)

        // getAllTasks
        lifecycleScope.launch {
            taskViewModel.tasks.collectLatest { tasks ->
                this@WidgetTaskSettingActivity.tasks = tasks
                val taskNames = tasks.map { it.getTaskName() }
                val adapter = ArrayAdapter(this@WidgetTaskSettingActivity, android.R.layout.simple_dropdown_item_1line, taskNames)
                fieldSpot1.setAdapter(adapter)
                fieldSpot2.setAdapter(adapter)
                fieldSpot3.setAdapter(adapter)
                fieldSpot4.setAdapter(adapter)

                // getTaskForId
                viewModel.widgetTasks.collectLatest { widgetTasks ->
                    var i = 0
                    while (i < 4 && i < widgetTasks.size) {
                        val currentTask = tasks.filter { it.taskId == widgetTasks[i].taskId }
                        i++
                        when (i) {
                            1 -> fieldSpot1.setText(currentTask.firstOrNull()?.getTaskName())
                            2 -> fieldSpot2.setText(currentTask.firstOrNull()?.getTaskName())
                            3 -> fieldSpot3.setText(currentTask.firstOrNull()?.getTaskName())
                            4 -> fieldSpot4.setText(currentTask.firstOrNull()?.getTaskName())
                        }
                    }
                }
            }
        }


        fieldSpot1.setOnItemClickListener{ parent, _, position, _ ->
            val selectedTaskName = parent.getItemAtPosition(position) as String
            val selectedTask = tasks.first { it.getTaskName() == selectedTaskName }
            setTaskForId(1, selectedTask.taskId, fieldSpot1)
        }

        fieldSpot2.setOnItemClickListener{ parent, _, position, _ ->
            val selectedTaskName = parent.getItemAtPosition(position) as String
            val selectedTask = tasks.first { it.getTaskName() == selectedTaskName }
            setTaskForId(2, selectedTask.taskId, fieldSpot2)
        }

        fieldSpot3.setOnItemClickListener{ parent, _, position, _ ->
            val selectedTaskName = parent.getItemAtPosition(position) as String
            val selectedTask = tasks.first { it.getTaskName() == selectedTaskName }
            setTaskForId(3, selectedTask.taskId, fieldSpot3)
        }

        fieldSpot4.setOnItemClickListener{ parent, _, position, _ ->
            val selectedTaskName = parent.getItemAtPosition(position) as String
            val selectedTask = tasks.first { it.getTaskName() == selectedTaskName }
            setTaskForId(4, selectedTask.taskId, fieldSpot4)
        }

        clearAllButton.setOnClickListener {
            viewModel.clearAllWidgetTasks()
            fieldSpot1.setText("")
            fieldSpot2.setText("")
            fieldSpot3.setText("")
            fieldSpot4.setText("")
        }

        backButton.setOnClickListener {
            finish()
        }

        addNewTaskButton.setOnClickListener {
            val intent = Intent(this, AddTaskActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        taskViewModel.getAllTasks()
        viewModel.getAllWidgetTasks()
    }

    private fun setTaskForId(id: Int, taskId: Int, field: AutoCompleteTextView) {
        lifecycleScope.launch {
            val widgetTasks = viewModel.widgetTasks.value
            if (widgetTasks.any { it.taskId == taskId }) {
                Toast.makeText(this@WidgetTaskSettingActivity, "Task already selected", Toast.LENGTH_SHORT).show()
                field.setText("")
                viewModel.setTaskForId(id, 0)
            } else {
                viewModel.setTaskForId(id, taskId)
            }
        }
    }
}

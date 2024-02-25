package ch.bfh.cas.mad.tasktimetrackerapp.activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.core.content.edit
import ch.bfh.cas.mad.tasktimetrackerapp.persistence.DataStore
import ch.bfh.cas.mad.tasktimetrackerapp.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

class WidgetTaskSettingActivity : ComponentActivity() {

    private lateinit var fabBack: FloatingActionButton
    private lateinit var fieldSpot1: AutoCompleteTextView
    private lateinit var fieldSpot2: AutoCompleteTextView
    private lateinit var fieldSpot3: AutoCompleteTextView
    private lateinit var fieldSpot4: AutoCompleteTextView
    private lateinit var clearAllButton: Button
    private lateinit var addNewTaskButton: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_widgettasksetting)

        fabBack = findViewById(R.id.fabBack)
        fieldSpot1 = findViewById(R.id.fieldSelectSpot1)
        fieldSpot2 = findViewById(R.id.fieldSelectSpot2)
        fieldSpot3 = findViewById(R.id.fieldSelectSpot3)
        fieldSpot4 = findViewById(R.id.fieldSelectSpot4)
        clearAllButton = findViewById(R.id.buttonClearAll)
        addNewTaskButton = findViewById(R.id.AddNewTask)

        val tasks = DataStore.tasks.map { it.name }

        val adapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, tasks)

        val sharedPreferences: SharedPreferences = getSharedPreferences("selectedTasks", Context.MODE_PRIVATE)

        fieldSpot1.setAdapter(adapter)
        fieldSpot2.setAdapter(adapter)
        fieldSpot3.setAdapter(adapter)
        fieldSpot4.setAdapter(adapter)

        fieldSpot1.setText(sharedPreferences.getString("selectedTask1", ""))
        fieldSpot2.setText(sharedPreferences.getString("selectedTask2", ""))
        fieldSpot3.setText(sharedPreferences.getString("selectedTask3", ""))
        fieldSpot4.setText(sharedPreferences.getString("selectedTask4", ""))

        fieldSpot1.setOnItemClickListener { _, _, _, _ ->
            checkTaskSelection(fieldSpot1.text.toString(), 0, sharedPreferences, fieldSpot1)
        }
        fieldSpot2.setOnItemClickListener { _, _, _, _ ->
            checkTaskSelection(fieldSpot2.text.toString(), 1, sharedPreferences, fieldSpot2)
        }
        fieldSpot3.setOnItemClickListener { _, _, _, _ ->
            checkTaskSelection(fieldSpot3.text.toString(), 2, sharedPreferences, fieldSpot3)
        }
        fieldSpot4.setOnItemClickListener { _, _, _, _ ->
            checkTaskSelection(fieldSpot4.text.toString(), 3, sharedPreferences, fieldSpot4)
        }

        fabBack.setOnClickListener {
            Intent(this, MainActivity::class.java).also {
                startActivity(it)
            }
        }

        clearAllButton.setOnClickListener {
            sharedPreferences.edit {
                putString("selectedTask1", "")
                putString("selectedTask2", "")
                putString("selectedTask3", "")
                putString("selectedTask4", "")
            }
            fieldSpot1.setText("")
            fieldSpot2.setText("")
            fieldSpot3.setText("")
            fieldSpot4.setText("")
        }

        addNewTaskButton.setOnClickListener {
            Intent(this, AddTaskActivity::class.java).also {
                startActivity(it)
            }
        }
    }

    private fun checkTaskSelection(selectedTask: String, position: Int, sharedPreferences: SharedPreferences, autoCompleteTextView: AutoCompleteTextView) {
        val selectedTasks = listOf(
            sharedPreferences.getString("selectedTask1", ""),
            sharedPreferences.getString("selectedTask2", ""),
            sharedPreferences.getString("selectedTask3", ""),
            sharedPreferences.getString("selectedTask4", "")
        )

        if (selectedTasks.filterNotNull().contains(selectedTask)) {
            autoCompleteTextView.setText("")
            Toast.makeText(this, "Task bereits ausgewählt", Toast.LENGTH_SHORT).show()
        } else {
            sharedPreferences.edit().putString("selectedTask${position + 1}", selectedTask).apply()
        }
    }

}

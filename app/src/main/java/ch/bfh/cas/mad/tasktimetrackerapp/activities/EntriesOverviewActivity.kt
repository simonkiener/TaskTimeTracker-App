package ch.bfh.cas.mad.tasktimetrackerapp.activities

import android.app.DatePickerDialog
import android.os.Bundle
import android.provider.ContactsContract.Data
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.recyclerview.widget.RecyclerView
import ch.bfh.cas.mad.tasktimetrackerapp.DataStore
import ch.bfh.cas.mad.tasktimetrackerapp.EntryAdapter
import ch.bfh.cas.mad.tasktimetrackerapp.R
import ch.bfh.cas.mad.tasktimetrackerapp.ui.theme.TaskTimeTrackerAppTheme
import java.util.Calendar

class EntriesOverviewActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_entriesoverview)

        val entryAdapter = EntryAdapter(entries = emptyList())

        val tasks = DataStore.tasks.map { it.name }
        val projects = DataStore.projects.map { it.name }

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewEntries)
        recyclerView.adapter = entryAdapter


        val spinnerTask = findViewById<Spinner>(R.id.spinnerTask)
        val spinnerProject = findViewById<Spinner>(R.id.spinnerProject)

        val taskAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, tasks)
        val projectAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, projects)



        spinnerTask.adapter = taskAdapter
        spinnerProject.adapter = projectAdapter

        val textViewStartDate = findViewById<TextView>(R.id.textViewStartDate)
        val textViewEndDate = findViewById<TextView>(R.id.textViewEndDate)

        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        spinnerTask.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                val selectedTask = parent.getItemAtPosition(position).toString()
                val selectedProject = spinnerProject.selectedItem.toString()
                entryAdapter.filterList(selectedTask, selectedProject)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Optionaler Code, wenn nichts ausgewählt ist
            }
        }

        spinnerProject.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>,
                view: View,
                position: Int,
                id: Long
            ) {
                val selectedProject = parent.getItemAtPosition(position).toString()
                val selectedTask = spinnerTask.selectedItem.toString()
                entryAdapter.filterList(selectedTask, selectedProject)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Optionaler Code, wenn nichts ausgewählt ist
            }
        }

        textViewStartDate.setOnClickListener {
            val datePickerDialog = DatePickerDialog(this,
                { _, selectedYear, selectedMonth, selectedDay ->
                    textViewStartDate.text = "$selectedDay/${selectedMonth + 1}/$selectedYear"
                }, year, month, day)
            datePickerDialog.show()
        }

        textViewEndDate.setOnClickListener {
            val datePickerDialog = DatePickerDialog(this,
                { _, selectedYear, selectedMonth, selectedDay ->
                    textViewEndDate.text = "$selectedDay/${selectedMonth + 1}/$selectedYear"
                }, year, month, day)
            datePickerDialog.show()
        }
    }
}
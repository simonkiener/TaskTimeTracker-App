package ch.bfh.cas.mad.tasktimetrackerapp.activities

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.ComponentActivity

import ch.bfh.cas.mad.tasktimetrackerapp.R
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoField

class DetailEntryActivity : ComponentActivity() {

    private lateinit var saveButton: Button
    private lateinit var backButton: FloatingActionButton
    private lateinit var textViewTaskName: TextView
    private lateinit var textViewProject: TextView
    private lateinit var timeEdit: EditText
    private lateinit var dateEdit: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detailentry)

        val entryId = intent.getLongExtra("entryId" , 0)
        val task = intent.getStringExtra("task")
        val project = intent.getStringExtra("project")
        val timestamp = intent.getStringExtra("timestamp")

        textViewTaskName = findViewById(R.id.editTextTaskName)
        textViewProject = findViewById(R.id.editTextProject)
        timeEdit = findViewById(R.id.editTime)
        dateEdit = findViewById(R.id.editDate)
        saveButton = findViewById(R.id.saveButton)
        backButton = findViewById(R.id.fabBack)

        textViewTaskName.text = task
        textViewProject.text = project
        if (timestamp != null) {
            timeEdit.setText(timestamp.map { it }.joinToString("").substring(11, 16))
            dateEdit.setText(timestamp.map { it }.joinToString("").substring(0, 10))
        }

        dateEdit.setOnClickListener() {
            val currentTimestamp = dateEdit.text.toString()
            var year = 0
            var month = 0
            var day = 0

            if (currentTimestamp.isNotEmpty()) {
                val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
                // Extract the year, month and day from the LocalDateTime
                year = formatter.parse(currentTimestamp).get(ChronoField.YEAR)
                month = formatter.parse(currentTimestamp).get(ChronoField.MONTH_OF_YEAR)
                day = formatter.parse(currentTimestamp).get(ChronoField.DAY_OF_MONTH)
            }

            val datePickerDialog = DatePickerDialog(this, { _, selectedYear, selectedMonth, selectedDay ->
                dateEdit.setText(String.format("%02d.%02d.%04d", selectedDay, selectedMonth + 1, selectedYear))
            }, year, month - 1, day)

            datePickerDialog.show()
        }


        timeEdit.setOnClickListener {
            val currentTimestamp = timeEdit.text.toString()
            var hour = 0
            var minute = 0

            if (currentTimestamp.isNotEmpty()) {
                val formatter = DateTimeFormatter.ofPattern("HH:mm")

                // Extract the hour and minute from the LocalDateTime
                hour = formatter.parse(currentTimestamp).get(ChronoField.HOUR_OF_DAY)
                minute = formatter.parse(currentTimestamp).get(ChronoField.MINUTE_OF_HOUR)
            }

            val timePickerDialog = TimePickerDialog(this, { _, selectedHour, selectedMinute ->
                timeEdit.setText(String.format("%02d:%02d", selectedHour, selectedMinute))
            }, hour, minute, true)

            timePickerDialog.show()
        }

        saveButton.setOnClickListener {
            //TODO: Save the changes to DB
            //updateEntry(entryId, dateEdit.text.toString(), timeEdit.text.toString())
            finish()
        }



        backButton.setOnClickListener {
            finish()
        }
    }
}
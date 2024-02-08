package ch.bfh.cas.mad.tasktimetrackerapp.activities

import android.os.Bundle
import android.widget.EditText
import androidx.activity.ComponentActivity

import ch.bfh.cas.mad.tasktimetrackerapp.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

class DetailEntryActivity : ComponentActivity() {

    private lateinit var editEntryButton: FloatingActionButton
    private lateinit var backButton: FloatingActionButton
    private lateinit var editTextTaskName: EditText
    private lateinit var editTextProject: EditText
    private lateinit var editTextStartDateTime: EditText
    private lateinit var editTextEndDateTime: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detailentry)

        val task = intent.getStringExtra("task")
        val project = intent.getStringExtra("project")

        editTextTaskName = findViewById(R.id.editTextTaskName)
        editTextProject = findViewById(R.id.editTextProject)
        editTextStartDateTime = findViewById(R.id.editTextStartDateTime)
        editTextEndDateTime = findViewById(R.id.editTextEndDateTime)
        editEntryButton = findViewById(R.id.EditEntryButton);
        backButton = findViewById(R.id.fabBack)

        editTextTaskName.setText(task)
        editTextProject.setText(project)


        //TODO: soll hier wirklich über diesen button das editieren aktiviert werden?
        editEntryButton.setOnClickListener { view ->
            val editTextTaskName = findViewById<EditText>(R.id.editTextTaskName)
            val editTextProject = findViewById<EditText>(R.id.editTextProject)
            val editTextStartDateTime = findViewById<EditText>(R.id.editTextStartDateTime)
            val editTextEndDateTime = findViewById<EditText>(R.id.editTextEndDateTime)
        }

        backButton.setOnClickListener {
            finish()
        }
    }
}
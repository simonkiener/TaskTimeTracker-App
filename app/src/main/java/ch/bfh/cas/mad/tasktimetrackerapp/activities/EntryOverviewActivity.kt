package ch.bfh.cas.mad.tasktimetrackerapp.activities

import android.app.DatePickerDialog
import android.os.Bundle
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ch.bfh.cas.mad.tasktimetrackerapp.adapter.EntryAdapter
import ch.bfh.cas.mad.tasktimetrackerapp.R
import ch.bfh.cas.mad.tasktimetrackerapp.persistence.EntryRepository
import ch.bfh.cas.mad.tasktimetrackerapp.persistence.TTTDatabaseProvider
import ch.bfh.cas.mad.tasktimetrackerapp.viewModel.EntryOverviewViewModel
import ch.bfh.cas.mad.tasktimetrackerapp.viewModel.EntryOverviewViewModelFactory
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.Calendar

class EntryOverviewActivity : ComponentActivity() {

    private lateinit var viewModel: EntryOverviewViewModel
    private lateinit var backButton: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_entriesoverview)

        val viewModelProvider = ViewModelProvider(
            this,
            EntryOverviewViewModelFactory(EntryRepository(TTTDatabaseProvider.get(this).getEntryDao()))
        )

        viewModel = viewModelProvider[EntryOverviewViewModel::class.java]

        backButton = findViewById(R.id.fabBack)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerViewEntries)
        recyclerView.layoutManager = LinearLayoutManager(this)
        lifecycleScope.launch {
            viewModel.entries.collectLatest { entries ->
                val adapter = EntryAdapter(entries = entries)
                recyclerView.adapter = adapter
            }
        }





        val textViewStartDate = findViewById<TextView>(R.id.textViewStartDate)
        val textViewEndDate = findViewById<TextView>(R.id.textViewEndDate)

        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

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

        backButton.setOnClickListener {
            finish()
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.getAllEntries()
    }
}
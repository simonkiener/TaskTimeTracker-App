package ch.bfh.cas.mad.tasktimetrackerapp.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ch.bfh.cas.mad.tasktimetrackerapp.DataStore
import ch.bfh.cas.mad.tasktimetrackerapp.adapter.EntryAdapter
import ch.bfh.cas.mad.tasktimetrackerapp.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

class DetailProjectActivity : ComponentActivity() {

    private lateinit var showAllEntriesButton: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detailproject)

        showAllEntriesButton = findViewById(R.id.btnShowAllEntries)

        val projectId = intent.getIntExtra("projectId", -1)
        val entries = DataStore.getEntriesForProject(projectId)
        val recyclerView = findViewById<RecyclerView>(R.id.entriesRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = EntryAdapter(entries)

        showAllEntriesButton.setOnClickListener {
            val intent = Intent(this, EntriesOverviewActivity::class.java)
            intent.putExtra("projectId", projectId)
            startActivity(intent)
        }
    }
}
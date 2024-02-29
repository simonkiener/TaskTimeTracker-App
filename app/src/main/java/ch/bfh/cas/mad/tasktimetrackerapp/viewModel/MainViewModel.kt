package ch.bfh.cas.mad.tasktimetrackerapp.viewModel

import android.widget.TextView
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ch.bfh.cas.mad.tasktimetrackerapp.R
import ch.bfh.cas.mad.tasktimetrackerapp.persistence.Entry
import ch.bfh.cas.mad.tasktimetrackerapp.persistence.EntryRepository
import ch.bfh.cas.mad.tasktimetrackerapp.persistence.WidgetTask
import ch.bfh.cas.mad.tasktimetrackerapp.persistence.WidgetTaskRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter

class MainViewModel (
    private val widgetTaskRepository: WidgetTaskRepository,
    private val entryRepository: EntryRepository
) : ViewModel() {
    private var _widgetTasks = MutableStateFlow(emptyList<WidgetTask>().toMutableList())
    val widgetTasks: MutableStateFlow<MutableList<WidgetTask>> = _widgetTasks

    private var _taskNames = MutableStateFlow(emptyList<String>().toMutableList())
    val taskNames: MutableStateFlow<MutableList<String>> = _taskNames

    fun getAllWidgetTasks() {
        viewModelScope.launch {
            _widgetTasks.value = widgetTaskRepository.getAllWidgetTasks()
        }
    }

    fun getTaskNames(lastWidgetTaskId: Int) {
        viewModelScope.launch {
            _taskNames.value.clear()
            var i = 1
            while (i <= lastWidgetTaskId) {
                val task = widgetTaskRepository.getTaskForId(i)
                if (task != null) {
                    _taskNames.value.add(task.name)
                } else
                {
                    _taskNames.value.add("NoTask")
                }
                i++
            }
        }
    }

    fun addEntryForWidget(widgetTaskId: Int) {
        viewModelScope.launch {
            // Check if current action is start or stop
            val entries = entryRepository.getAllEntries()
            val isStartAction = entries.size.mod(2) == 0

            // get current timestamp for entries to have the same for each entry
            val currentDateTimeInSeconds = LocalDateTime.now().atZone(ZoneOffset.UTC).toEpochSecond()

            // add stop entry if needed
            var stopEntryTaskId = -1
            if (!isStartAction) {
                val lastEntryTaskId = entries.last().taskId
                val stopEntry = Entry(0, "STOP", lastEntryTaskId, currentDateTimeInSeconds)
                stopEntryTaskId = stopEntry.taskId
                entryRepository.addEntry(stopEntry)
            }

            // add start entry if needed
            val currentTask = widgetTaskRepository.getTaskForId(widgetTaskId)
            if (currentTask != null) {
                if (isStartAction || stopEntryTaskId != currentTask.id) {
                    val startEntry = Entry(0, "START", currentTask.id, currentDateTimeInSeconds)
                    entryRepository.addEntry(startEntry)
                }
            }
        }
    }
}
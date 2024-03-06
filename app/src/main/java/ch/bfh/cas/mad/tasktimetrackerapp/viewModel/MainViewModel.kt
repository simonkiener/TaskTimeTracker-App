package ch.bfh.cas.mad.tasktimetrackerapp.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ch.bfh.cas.mad.tasktimetrackerapp.persistence.Entry
import ch.bfh.cas.mad.tasktimetrackerapp.persistence.EntryRepository
import ch.bfh.cas.mad.tasktimetrackerapp.persistence.WidgetTask
import ch.bfh.cas.mad.tasktimetrackerapp.persistence.WidgetTaskRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.ZoneOffset


class MainViewModel (
    val widgetTaskRepository: WidgetTaskRepository,
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

    fun getTaskNames(noOfWidgetTasks: Int) {
        viewModelScope.launch {
            var index = 1

            // Avoiding a growing list by clearing or reinitializing with an empty list doesn't work.
            // Check position in list and overwrite instead
            while (index <= noOfWidgetTasks) {
                val task = widgetTaskRepository.getTaskForId(index)
                var currentText = "---"
                if (task != null) {
                    currentText = task.name
                }

                // Initialize entry if needed
                if (_taskNames.value.elementAtOrNull(index - 1) == null) {
                    _taskNames.value.add("")
                }

                // Assign value
                _taskNames.value[index - 1] = currentText

                index++
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
                if (isStartAction || stopEntryTaskId != currentTask.taskId) {
                    val startEntry = Entry(0, "START", currentTask.taskId, currentDateTimeInSeconds)
                    entryRepository.addEntry(startEntry)
                }
            }
        }
    }
}
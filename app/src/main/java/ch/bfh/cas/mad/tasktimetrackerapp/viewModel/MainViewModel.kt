package ch.bfh.cas.mad.tasktimetrackerapp.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ch.bfh.cas.mad.tasktimetrackerapp.persistence.Entry
import ch.bfh.cas.mad.tasktimetrackerapp.persistence.EntryRepository
import ch.bfh.cas.mad.tasktimetrackerapp.persistence.WidgetTask
import ch.bfh.cas.mad.tasktimetrackerapp.persistence.WidgetTaskRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

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
            val task = widgetTaskRepository.getTaskForId(widgetTaskId)
            val entry = task?.let { Entry(0, "", it.id, System.currentTimeMillis()) }
            if (entry != null) {
                entryRepository.addEntry(entry)
            }
        }
    }
}
package ch.bfh.cas.mad.tasktimetrackerapp.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ch.bfh.cas.mad.tasktimetrackerapp.persistence.WidgetTask
import ch.bfh.cas.mad.tasktimetrackerapp.persistence.WidgetTaskRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class WidgetTaskSettingViewModel (
    private val widgetTaskRepository: WidgetTaskRepository
) : ViewModel() {
    private var _widgetTasks = MutableStateFlow(emptyList<WidgetTask>().toMutableList())
    val widgetTasks: MutableStateFlow<MutableList<WidgetTask>> = _widgetTasks

    private var _taskName = MutableStateFlow("")
    val taskName: MutableStateFlow<String> = _taskName

    fun getAllWidgetTasks() {
        viewModelScope.launch {
            _widgetTasks.value = widgetTaskRepository.getAllWidgetTasks()
        }
    }

    fun setTaskForId(widgetTaskId: Int, taskId: Int) {
        viewModelScope.launch {
            widgetTaskRepository.setTaskForId(widgetTaskId, taskId)
        }
    }

    fun clearAllWidgetTasks() {
        viewModelScope.launch {
            widgetTaskRepository.clearAllWidgetTasks()
        }
    }

    fun getTaskName(widgetTaskId: Int) {
        viewModelScope.launch {
            val task = widgetTaskRepository.getTaskForId(widgetTaskId)
            if (task != null) {
                _taskName.value = task.name
            }
        }
    }
}
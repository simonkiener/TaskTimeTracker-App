package ch.bfh.cas.mad.tasktimetrackerapp.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ch.bfh.cas.mad.tasktimetrackerapp.persistence.WidgetTask
import ch.bfh.cas.mad.tasktimetrackerapp.persistence.WidgetTaskRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class MainViewModel (
    private val widgetTaskRepository: WidgetTaskRepository
) : ViewModel() {
    private var _widgetTasks = MutableStateFlow(emptyList<WidgetTask>().toMutableList())
    val widgetTasks: MutableStateFlow<MutableList<WidgetTask>> = _widgetTasks

    fun getAllWidgetTasks() {
        viewModelScope.launch {
            _widgetTasks.value = widgetTaskRepository.getAllWidgetTasks()
        }
    }
}
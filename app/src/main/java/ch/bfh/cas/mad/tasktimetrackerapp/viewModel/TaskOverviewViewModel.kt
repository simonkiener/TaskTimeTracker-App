package ch.bfh.cas.mad.tasktimetrackerapp.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ch.bfh.cas.mad.tasktimetrackerapp.persistence.Task
import ch.bfh.cas.mad.tasktimetrackerapp.persistence.TaskRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class TaskOverviewViewModel (
    private val taskRepository: TaskRepository
) : ViewModel() {
    private var _tasks = MutableStateFlow(emptyList<Task>().toMutableList())
    val tasks: MutableStateFlow<MutableList<Task>> = _tasks

    fun getAllTasks() {
        viewModelScope.launch {
            _tasks.value = taskRepository.getAllTasks()
        }
    }

    fun getTasksForProject(projectId: Int) {
        viewModelScope.launch {
            _tasks.value = taskRepository.getTasksForProject(projectId)
        }
    }

    fun deleteAllTasks() {
        viewModelScope.launch {
            taskRepository.deleteAllTasks()
        }
    }
}
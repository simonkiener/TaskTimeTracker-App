package ch.bfh.cas.mad.tasktimetrackerapp.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ch.bfh.cas.mad.tasktimetrackerapp.persistence.Task
import ch.bfh.cas.mad.tasktimetrackerapp.persistence.TaskRepository
import kotlinx.coroutines.launch

class AddTaskViewModel (
    private val taskRepository: TaskRepository
) : ViewModel() {

        fun addTask(task: Task) {
            viewModelScope.launch {
                taskRepository.addTask(task)
            }
        }
}
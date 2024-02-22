package ch.bfh.cas.mad.tasktimetrackerapp.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ch.bfh.cas.mad.tasktimetrackerapp.persistence.ProjectRepository
import ch.bfh.cas.mad.tasktimetrackerapp.persistence.TaskRepository

class TaskDetailViewModelFactory(
    private val projectRepository: ProjectRepository,
    private val taskRepository: TaskRepository
)  : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return TaskDetailViewModel(taskRepository, projectRepository) as T
    }
}
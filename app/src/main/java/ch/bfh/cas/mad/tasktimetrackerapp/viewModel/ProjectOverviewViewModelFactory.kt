package ch.bfh.cas.mad.tasktimetrackerapp.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ch.bfh.cas.mad.tasktimetrackerapp.persistence.ProjectRepository

class ProjectOverviewViewModelFactory(
    private val projectRepository: ProjectRepository
)  : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ProjectOverviewViewModel(projectRepository) as T
    }
}
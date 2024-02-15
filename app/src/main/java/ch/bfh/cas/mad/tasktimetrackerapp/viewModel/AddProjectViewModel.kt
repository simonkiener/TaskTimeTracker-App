package ch.bfh.cas.mad.tasktimetrackerapp.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ch.bfh.cas.mad.tasktimetrackerapp.persistence.Project
import ch.bfh.cas.mad.tasktimetrackerapp.persistence.ProjectRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class AddProjectViewModel (
    private val projectRepository: ProjectRepository
) : ViewModel() {

        fun addProject(project: Project) {
            viewModelScope.launch {
                projectRepository.addProject(project)
            }
        }
}
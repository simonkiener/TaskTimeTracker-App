package ch.bfh.cas.mad.tasktimetrackerapp.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ch.bfh.cas.mad.tasktimetrackerapp.persistence.Project
import ch.bfh.cas.mad.tasktimetrackerapp.persistence.ProjectRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class ProjectOverviewViewModel (
    private val projectRepository: ProjectRepository
) : ViewModel() {
    private var _projects = MutableStateFlow(emptyList<Project>().toMutableList())
    val projects: MutableStateFlow<MutableList<Project>> = _projects

    // ToDo: create 2nd constructor without parameters?
    private var _project = MutableStateFlow(Project(0, ""))
    val project: MutableStateFlow<Project> = _project

    fun getAllProjects() {
        viewModelScope.launch {
            _projects.value = projectRepository.getAllProjects()
        }
    }

    fun deleteAllProjects() {
        viewModelScope.launch {
            projectRepository.deleteAllProjects()
        }
    }
}
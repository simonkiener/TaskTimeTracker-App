package ch.bfh.cas.mad.tasktimetrackerapp.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ch.bfh.cas.mad.tasktimetrackerapp.persistence.Entry
import ch.bfh.cas.mad.tasktimetrackerapp.persistence.Project
import ch.bfh.cas.mad.tasktimetrackerapp.persistence.ProjectRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class ProjectDetailViewModel (
    private val projectRepository: ProjectRepository
) : ViewModel() {
    private var _projects = MutableStateFlow(emptyList<Project>().toMutableList())
    val projects: MutableStateFlow<MutableList<Project>> = _projects

    private var _entries = MutableStateFlow(emptyList<Entry>().toMutableList())
    val entries: MutableStateFlow<MutableList<Entry>> = _entries

    private var _projectName = MutableStateFlow("")
    val projectName: MutableStateFlow<String> = _projectName

    fun getEntriesForProject(projectId: Int) {
        viewModelScope.launch {
            _entries.value = projectRepository.getEntriesForProject(projectId = projectId)
        }
    }

    fun getProjectName(projectId: Int) {
        viewModelScope.launch {
            val project = projectRepository.getProject(projectId = projectId)
            _projectName.value = project.name
        }
    }
}
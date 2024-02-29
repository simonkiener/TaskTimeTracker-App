package ch.bfh.cas.mad.tasktimetrackerapp.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ch.bfh.cas.mad.tasktimetrackerapp.persistence.Entry
import ch.bfh.cas.mad.tasktimetrackerapp.persistence.ProjectRepository
import ch.bfh.cas.mad.tasktimetrackerapp.persistence.Task
import ch.bfh.cas.mad.tasktimetrackerapp.persistence.TaskRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class TaskDetailViewModel (
    private val taskRepository: TaskRepository,
    private val projectRepository: ProjectRepository
) : ViewModel() {
    private var _tasks = MutableStateFlow(emptyList<Task>().toMutableList())
    val tasks: MutableStateFlow<MutableList<Task>> = _tasks

    private var _entries = MutableStateFlow(emptyList<Entry>().toMutableList())
    val entries: MutableStateFlow<MutableList<Entry>> = _entries

    private var _taskName = MutableStateFlow("")
    val taskName: MutableStateFlow<String> = _taskName

    private var _projectName = MutableStateFlow("")
    val projectName: MutableStateFlow<String> = _projectName

    fun getTasks() {
        viewModelScope.launch {
            _tasks.value = taskRepository.getAllTasks()
        }
    }

    fun getEntriesForTask(taskId: Int) {
        viewModelScope.launch {
            _entries.value = taskRepository.getEntriesForTask(taskId = taskId)
        }
    }

    fun getTaskName(taskId: Int) {
        viewModelScope.launch {
            val task = taskRepository.getTask(taskId = taskId)
            _taskName.value = task.name
        }
    }

    fun getProjectName(taskId: Int) {
        viewModelScope.launch {
            val task = taskRepository.getTask(taskId = taskId)
            val project = projectRepository.getProject(task.projectId)
            _projectName.value = project.name
        }
    }
}
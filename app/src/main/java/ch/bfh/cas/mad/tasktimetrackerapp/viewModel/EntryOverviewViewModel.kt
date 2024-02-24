package ch.bfh.cas.mad.tasktimetrackerapp.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ch.bfh.cas.mad.tasktimetrackerapp.persistence.Entry
import ch.bfh.cas.mad.tasktimetrackerapp.persistence.EntryRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.util.Date

class EntryOverviewViewModel (
    private val entryRepository: EntryRepository
) : ViewModel() {
    private var _entries = MutableStateFlow(emptyList<Entry>().toMutableList())
    val entries: MutableStateFlow<MutableList<Entry>> = _entries

    fun getAllEntries() {
        viewModelScope.launch {
            _entries.value = entryRepository.getAllEntries()
        }
    }

    fun getAllEntries(startDate: Long, endDate: Long) {
        viewModelScope.launch {
            _entries.value = entryRepository.getAllEntries(startDate, endDate)
        }
    }

    fun getEntriesForTask(taskId: Int) {
        viewModelScope.launch {
            _entries.value = entryRepository.getEntriesForTask(taskId)
        }
    }

    fun getEntriesForTask(startDate: Long, endDate: Long, taskId: Int) {
        viewModelScope.launch {
            _entries.value = entryRepository.getEntriesForTask(startDate, endDate, taskId)
        }
    }

    fun getEntriesForProject(projectId: Int) {
        viewModelScope.launch {
            _entries.value = entryRepository.getEntriesForProject(projectId)
        }
    }

    fun getEntriesForProject(startDate: Long, endDate: Long, projectId: Int) {
        viewModelScope.launch {
            _entries.value = entryRepository.getEntriesForProject(startDate, endDate, projectId)
        }
    }

    fun deleteAllEntries() {
        viewModelScope.launch {
            entryRepository.deleteAllEntries()
        }
    }
}
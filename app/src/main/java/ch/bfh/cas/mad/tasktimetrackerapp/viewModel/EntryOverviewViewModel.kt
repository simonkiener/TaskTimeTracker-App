package ch.bfh.cas.mad.tasktimetrackerapp.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ch.bfh.cas.mad.tasktimetrackerapp.persistence.Entry
import ch.bfh.cas.mad.tasktimetrackerapp.persistence.EntryRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

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

    fun getEntriesForTask(taskId: Int) {
        viewModelScope.launch {
            _entries.value = entryRepository.getEntriesForTask(taskId)
        }
    }

    fun getEntriesForProject(projectId: Int) {
        viewModelScope.launch {
            _entries.value = entryRepository.getEntriesForProject(projectId)
        }
    }

    fun deleteAllEntries() {
        viewModelScope.launch {
            entryRepository.deleteAllEntries()
        }
    }
}
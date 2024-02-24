package ch.bfh.cas.mad.tasktimetrackerapp.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ch.bfh.cas.mad.tasktimetrackerapp.persistence.Entry
import ch.bfh.cas.mad.tasktimetrackerapp.persistence.EntryRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class EntryOverviewViewModel (
    private val entryRepository: EntryRepository
) : ViewModel() {
    private val zero = 0.toLong()

    private var _entries = MutableStateFlow(emptyList<Entry>().toMutableList())
    val entries: MutableStateFlow<MutableList<Entry>> = _entries

    private var _totalTime = MutableStateFlow(zero)
    val totalTime: MutableStateFlow<Long> = _totalTime

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

    fun getTotalTime() {
        viewModelScope.launch {
            entries.collectLatest {myEntries ->
                if (myEntries.size.mod(2) > 0) {
                    myEntries.removeLast()
                }

                if (myEntries.size > 0) {
                    var currentTimeStamp = myEntries[0].timeStamp
                    var total = zero
                    myEntries.forEach {
                        total += it.timeStamp - currentTimeStamp
                        currentTimeStamp = it.timeStamp
                    }

                    _totalTime.value = total
                }
            }
        }
    }

    fun deleteAllEntries() {
        viewModelScope.launch {
            entryRepository.deleteAllEntries()
        }
    }
}
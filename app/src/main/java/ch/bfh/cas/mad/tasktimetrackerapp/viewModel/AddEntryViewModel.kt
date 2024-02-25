package ch.bfh.cas.mad.tasktimetrackerapp.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ch.bfh.cas.mad.tasktimetrackerapp.persistence.Entry
import ch.bfh.cas.mad.tasktimetrackerapp.persistence.EntryRepository
import kotlinx.coroutines.launch

class AddEntryViewModel (
    private val entryRepository: EntryRepository
) : ViewModel() {

        fun addEntry(entry: Entry) {
            viewModelScope.launch {
                entryRepository.addEntry(entry)
            }
        }
}
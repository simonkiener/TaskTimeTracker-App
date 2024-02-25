package ch.bfh.cas.mad.tasktimetrackerapp.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ch.bfh.cas.mad.tasktimetrackerapp.persistence.EntryRepository

class EntryOverviewViewModelFactory(
    private val entryRepository: EntryRepository
)  : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return EntryOverviewViewModel(entryRepository) as T
    }
}
package ch.bfh.cas.mad.tasktimetrackerapp.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ch.bfh.cas.mad.tasktimetrackerapp.persistence.DatabaseRepository

class InitDatabaseViewModelFactory(
    private val databaseRepository: DatabaseRepository
)  : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return InitDatabaseViewModel(databaseRepository) as T
    }
}
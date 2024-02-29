package ch.bfh.cas.mad.tasktimetrackerapp.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ch.bfh.cas.mad.tasktimetrackerapp.persistence.EntryRepository
import ch.bfh.cas.mad.tasktimetrackerapp.persistence.WidgetTaskRepository

class MainViewModelFactory(
    private val widgetTaskRepository: WidgetTaskRepository,
    private val entryRepository: EntryRepository
)  : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(widgetTaskRepository, entryRepository) as T
    }
}
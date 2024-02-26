package ch.bfh.cas.mad.tasktimetrackerapp.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ch.bfh.cas.mad.tasktimetrackerapp.persistence.WidgetTaskRepository

class WidgetTaskSettingViewModelFactory(
    private val widgetTaskRepository: WidgetTaskRepository
)  : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return WidgetTaskSettingViewModel(widgetTaskRepository) as T
    }
}
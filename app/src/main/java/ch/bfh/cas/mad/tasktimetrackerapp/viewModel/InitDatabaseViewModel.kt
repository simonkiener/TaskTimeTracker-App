package ch.bfh.cas.mad.tasktimetrackerapp.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ch.bfh.cas.mad.tasktimetrackerapp.persistence.DatabaseRepository
import ch.bfh.cas.mad.tasktimetrackerapp.persistence.Project
import ch.bfh.cas.mad.tasktimetrackerapp.persistence.ProjectRepository
import kotlinx.coroutines.launch

class InitDatabaseViewModel (
    private val databaseRepository: DatabaseRepository
) : ViewModel() {

        fun initDatabase() {
            viewModelScope.launch {
                databaseRepository.initDatabase()
            }
        }
}
package ch.bfh.cas.mad.tasktimetrackerapp.persistence

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DatabaseRepository(
    private val projectDao: ProjectDao,
    private val taskDao: TaskDao,
    private val entryDao: EntryDao,
    private val widgetTaskDao: WidgetTaskDao
) {
    suspend fun initDatabase() {
        // Delete all data
        projectDao.deleteAll()
        taskDao.deleteAll()
        entryDao.deleteAll()
        widgetTaskDao.deleteAll()



        // Add projects
        DataStore.projects.forEach {
            projectDao.insert(it)
        }

        // Add tasks
        DataStore.tasks.forEach {
            taskDao.insert(it)
        }

        // Add entries
//        DataStore.entries.forEach {
//            entryDao.insert(it)
//        }

        // Add widgetTasks
//        DataStore.widgetTasks.forEach {
//            widgetTaskDao.insert(it)
//        }
    }
}
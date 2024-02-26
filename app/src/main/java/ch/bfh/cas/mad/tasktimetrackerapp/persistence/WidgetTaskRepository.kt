package ch.bfh.cas.mad.tasktimetrackerapp.persistence

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class WidgetTaskRepository(
    private val widgetTaskDao: WidgetTaskDao
) {
    suspend fun getAllWidgetTasks(): MutableList<WidgetTask> = withContext(Dispatchers.IO) {
        val storedWidgetTasks = widgetTaskDao.getAllWidgetTasks()
        if (storedWidgetTasks.isNotEmpty()) {
            return@withContext storedWidgetTasks
        }

        return@withContext emptyList<WidgetTask>().toMutableList()
    }

    suspend fun getTaskForId(widgetTaskId: Int): Int = withContext(Dispatchers.IO) {
        return@withContext widgetTaskDao.getTaskIdForId(widgetTaskId)
    }

    suspend fun setTaskForId(widgetTaskId: Int, taskId: Int) {
        widgetTaskDao.setTaskForId(widgetTaskId, taskId)
    }

    suspend fun clearAllWidgetTasks() {
        widgetTaskDao.deleteAll()
    }
}
package ch.bfh.cas.mad.tasktimetrackerapp.persistence

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TaskRepository(
    private val taskDao: TaskDao
) {
    suspend fun getAllTasks(): MutableList<Task> = withContext(Dispatchers.IO) {
        val storedTasks = taskDao.getAll()
        if (storedTasks.isNotEmpty()) {
            return@withContext storedTasks
        }

        return@withContext emptyList<Task>().toMutableList()
    }

    suspend fun deleteAllTasks() {
        getAllTasks().forEach {
            taskDao.delete(it)
        }
    }

    suspend fun addTask(task: Task)
    {
        taskDao.insert(task = task)
    }
}
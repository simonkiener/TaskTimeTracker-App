package ch.bfh.cas.mad.tasktimetrackerapp.persistence

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TaskRepository(
    private val taskDao: TaskDao,
    private val entryDao: EntryDao
) {
    suspend fun getAllTasks(): MutableList<Task> = withContext(Dispatchers.IO) {
        val storedTasks = taskDao.getAll()
        if (storedTasks.isNotEmpty()) {
            return@withContext storedTasks
        }

        return@withContext emptyList<Task>().toMutableList()
    }

    suspend fun getTasksForProject(projectId: Int): MutableList<Task> = withContext(Dispatchers.IO) {
        val storedTasks = taskDao.getTasksForProject(projectId)
        if (storedTasks.isNotEmpty()) {
            return@withContext storedTasks
        }

        return@withContext emptyList<Task>().toMutableList()
    }

    suspend fun getEntriesForTask(taskId: Int): MutableList<Entry> = withContext(Dispatchers.IO) {
        val storedEntries = entryDao.getEntriesForTask(taskId)
        if (storedEntries.isNotEmpty()) {
            return@withContext storedEntries
        }

        return@withContext emptyList<Entry>().toMutableList()
    }

    suspend fun getTask(taskId: Int):Task {
        return taskDao.getTask(taskId)
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
package ch.bfh.cas.mad.tasktimetrackerapp.persistence

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.Date

class EntryRepository(
    private val entryDao: EntryDao
) {
    suspend fun getAllEntries(): MutableList<Entry> = withContext(Dispatchers.IO) {
        val storedEntries = entryDao.getAllEntries()
        if (storedEntries.isNotEmpty()) {
            return@withContext storedEntries
        }

        return@withContext emptyList<Entry>().toMutableList()
    }

    suspend fun getAllEntries(startDate: Long, endDate: Long): MutableList<Entry> = withContext(Dispatchers.IO) {
        val storedEntries = entryDao.getAllEntries(startDate, endDate)
        if (storedEntries.isNotEmpty()) {
            return@withContext storedEntries
        }

        return@withContext emptyList<Entry>().toMutableList()
    }

    suspend fun getEntriesForTask(taskId: Int): MutableList<Entry> = withContext(Dispatchers.IO) {
        val storedEntries = entryDao.getEntriesForTask(taskId)
        if (storedEntries.isNotEmpty()) {
            return@withContext storedEntries
        }

        return@withContext emptyList<Entry>().toMutableList()
    }

    suspend fun getEntriesForTask(startDate: Long, endDate: Long, taskId: Int): MutableList<Entry> = withContext(Dispatchers.IO) {
        val storedEntries = entryDao.getEntriesForTask(startDate, endDate, taskId)
        if (storedEntries.isNotEmpty()) {
            return@withContext storedEntries
        }

        return@withContext emptyList<Entry>().toMutableList()
    }

    suspend fun getEntriesForProject(projectId: Int): MutableList<Entry> = withContext(Dispatchers.IO) {
        val storedEntries = entryDao.getEntriesForProject(projectId)
        if (storedEntries.isNotEmpty()) {
            return@withContext storedEntries
        }

        return@withContext emptyList<Entry>().toMutableList()
    }

    suspend fun getEntriesForProject(startDate: Long, endDate: Long, projectId: Int): MutableList<Entry> = withContext(Dispatchers.IO) {
        val storedEntries = entryDao.getEntriesForProject(startDate, endDate, projectId)
        if (storedEntries.isNotEmpty()) {
            return@withContext storedEntries
        }

        return@withContext emptyList<Entry>().toMutableList()
    }

    suspend fun deleteAllEntries() {
        entryDao.deleteAll()
    }

    suspend fun addEntry(entry: Entry)
    {
        entryDao.insert(entry = entry)
    }
}
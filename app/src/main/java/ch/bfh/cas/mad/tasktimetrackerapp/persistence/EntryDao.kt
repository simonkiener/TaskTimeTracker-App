package ch.bfh.cas.mad.tasktimetrackerapp.persistence

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface EntryDao {
    // Create
    @Insert
    suspend fun insert(entry: Entry)

    // Read
    @Query("SELECT * FROM entry")
    suspend fun getAllEntries(): MutableList<Entry>

    @Query("SELECT * FROM entry WHERE timeStamp >= :startDate AND timeStamp <= :endDate")
    suspend fun getAllEntries(startDate: Long, endDate: Long): MutableList<Entry>

    @Query("SELECT * FROM entry WHERE taskId = :taskId")
    suspend fun getEntriesForTask(taskId: Int): MutableList<Entry>

    @Query("SELECT * FROM entry WHERE timeStamp >= :startDate AND timeStamp <= :endDate AND taskId = :taskId")
    suspend fun getEntriesForTask(startDate: Long, endDate: Long, taskId: Int): MutableList<Entry>

    @Query("SELECT * FROM entry JOIN task ON entry.taskId = task.taskId WHERE task.projectId = :projectId")
    suspend fun getEntriesForProject(projectId: Int): MutableList<Entry>

    @Query("SELECT * FROM entry JOIN task ON entry.taskId = task.taskId WHERE timeStamp >= :startDate AND timeStamp <= :endDate AND task.projectId = :projectId")
    suspend fun getEntriesForProject(startDate: Long, endDate: Long, projectId: Int): MutableList<Entry>

    // Update

    // Delete
    @Query("DELETE FROM entry")
    suspend fun deleteAll()
}
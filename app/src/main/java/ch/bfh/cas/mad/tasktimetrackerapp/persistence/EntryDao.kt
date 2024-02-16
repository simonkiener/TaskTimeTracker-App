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

    // ToDo: change query to get entries from project only
    @Query("SELECT * FROM entry WHERE taskId = :projectId")
    suspend fun getAllEntriesForProject(projectId: Int): MutableList<Entry>

    // Update

    // Delete
    @Query("DELETE FROM entry")
    suspend fun deleteAll()
}
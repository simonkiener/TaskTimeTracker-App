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
    @Query("SELECT * from entry")
    suspend fun getAllEntries(): List<Entry>

    // Update

    // Delete

}